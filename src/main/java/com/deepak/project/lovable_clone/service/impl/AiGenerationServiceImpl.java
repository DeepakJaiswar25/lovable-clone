package com.deepak.project.lovable_clone.service.impl;

import com.deepak.project.lovable_clone.entity.ChatSession;
import com.deepak.project.lovable_clone.entity.ChatSessionId;
import com.deepak.project.lovable_clone.entity.Project;
import com.deepak.project.lovable_clone.entity.User;
import com.deepak.project.lovable_clone.error.ResourceNotFoundException;
import com.deepak.project.lovable_clone.llm.advisors.FileTreeAdvisor;
import com.deepak.project.lovable_clone.llm.PromptUtils;
import com.deepak.project.lovable_clone.llm.tools.CodeGenerationTools;
import com.deepak.project.lovable_clone.repository.ChatSessionRepository;
import com.deepak.project.lovable_clone.repository.ProjectRepository;
import com.deepak.project.lovable_clone.repository.UserRepository;
import com.deepak.project.lovable_clone.security.AuthUtil;
import com.deepak.project.lovable_clone.service.AiGenerationService;
import com.deepak.project.lovable_clone.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiGenerationServiceImpl implements AiGenerationService {

    private final ChatClient chatClient;
    private final AuthUtil authUtil;
    private final ProjectFileService projectFileService;
    private final FileTreeAdvisor fileTreeAdvisor;
    private final ChatSessionRepository chatSessionRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    private static final Pattern FILE_TAG_PATTERN = Pattern.compile("<file path=\"([^\"]+)\">(.*?)</file>", Pattern.DOTALL);


    @Override
//    @PreAuthorize("@security.canEditProject(#projectId)")
    public Flux<String> streamResponse(String message, Long projectId) {

        Long userId = authUtil.getCurrentUserId();
        ChatSession chatSession= createChatSessionIfNotExists(projectId, userId);

        Map<String, Object> params = Map.of("userId",userId,"projectId", projectId);
        StringBuilder fullResponseBuffer=new StringBuilder();

        CodeGenerationTools codeGenerationTools= new CodeGenerationTools(projectFileService, projectId);
        return chatClient.prompt()
                .system(PromptUtils.CODE_GENERATION_SYSTEM_PROMPT)
                .user(message)
                .tools(codeGenerationTools)
                .advisors(
                        advisorSpec -> {
                            advisorSpec.params(params);
                            advisorSpec.advisors(fileTreeAdvisor);
                        }
                )
                .stream()
                .chatResponse()
                .filter(r -> r.getResult() != null && r.getResult().getOutput() != null)
                .doOnNext(response -> {
                    String content= response.getResult().getOutput().getText();
                    fullResponseBuffer.append(content);
                }
                )
                .doOnComplete(
                        () -> {
                           Schedulers.boundedElastic().schedule(()-> {
                                       parseAndSaveFiles(fullResponseBuffer.toString(), projectId);
                                   }
                           );

                        }
                )
                .doOnError(
                        error -> {
                            log.error("Error during streaming for projectId: {}", projectId,error);
                        }
                )
                .map(chatResponse -> chatResponse.getResult().getOutput().getText());
    }

    private ChatSession createChatSessionIfNotExists(Long projectId, Long userId) {

        ChatSessionId chatSessionId= new ChatSessionId(projectId,userId);
        ChatSession chatSession =chatSessionRepository.findById(chatSessionId).orElse(null);
        if (chatSession == null) {
            Project project= projectRepository.findById(projectId).orElseThrow(()-> new ResourceNotFoundException("Project", projectId.toString()));
            User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", userId.toString()));
            chatSession = ChatSession.builder()
                    .project(project)
                    .user(user)
                    .id(chatSessionId)
                    .build();
            chatSessionRepository.save(chatSession);
        }
        return chatSession;
    }

    private void parseAndSaveFiles(String fullResponse, Long projectId) {
        Matcher matcher= FILE_TAG_PATTERN.matcher(fullResponse);
     while (matcher.find()){
         String filePath= matcher.group(1);
         String fileContent= matcher.group(2).trim();
         projectFileService.saveFile(projectId,filePath,fileContent);
     }
    }
}
