package com.deepak.project.lovable_clone.service.impl;

import com.deepak.project.lovable_clone.dto.chat.ChatResponse;
import com.deepak.project.lovable_clone.entity.ChatMessage;
import com.deepak.project.lovable_clone.entity.ChatSession;
import com.deepak.project.lovable_clone.entity.ChatSessionId;
import com.deepak.project.lovable_clone.mapper.ChatMapper;
import com.deepak.project.lovable_clone.repository.ChatMessageRepository;
import com.deepak.project.lovable_clone.repository.ChatSessionRepository;
import com.deepak.project.lovable_clone.security.AuthUtil;
import com.deepak.project.lovable_clone.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatMapper chatMapper;

    private final AuthUtil authUtil;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatSessionRepository chatSessionRepository;
    @Override
    public List<ChatResponse> getProjectChatHistory(Long projectId) {

        Long userId =authUtil.getCurrentUserId();
        ChatSession chatSession= chatSessionRepository.getReferenceById(new ChatSessionId(projectId,userId));
        List<ChatMessage> chatMessageList= chatMessageRepository.findByChatSession(chatSession);
        return chatMapper.toChatResponseList(chatMessageList);
    }
}
