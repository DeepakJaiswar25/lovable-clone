package com.deepak.project.lovable_clone.service.impl;

import com.deepak.project.lovable_clone.dto.project.FileContentResponse;
import com.deepak.project.lovable_clone.dto.project.FileNode;
import com.deepak.project.lovable_clone.dto.project.FileTreeResponse;
import com.deepak.project.lovable_clone.entity.Project;
import com.deepak.project.lovable_clone.entity.ProjectFile;
import com.deepak.project.lovable_clone.error.ResourceNotFoundException;
import com.deepak.project.lovable_clone.mapper.ProjectFileMapper;
import com.deepak.project.lovable_clone.repository.ProjectFileRepository;
import com.deepak.project.lovable_clone.repository.ProjectRepository;
import com.deepak.project.lovable_clone.service.ProjectFileService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectFileServiceImpl implements ProjectFileService {

    private final ProjectFileRepository projectFileRepository;
    private final ProjectRepository projectRepository;

    @Value("${minio.bucket}")
    private String projectBucket;

    private static final String BUCKET_NAME = "projects";

    private final MinioClient minioClient;
    private final ProjectFileMapper projectFileMapper;
    @Override
    public FileTreeResponse getFileTree(Long projectId) {
        List<ProjectFile> projectFileList = projectFileRepository.findByProjectId(projectId);

        List<FileNode> fileNodes= projectFileMapper.toListOfFileNode(projectFileList);
        return new FileTreeResponse(fileNodes);
    }

    @Override
    public FileContentResponse getFileContent(Long projectId, String path) {
        String objectName = projectId + "/" + path;
        try (
                InputStream is = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(BUCKET_NAME)
                                .object(objectName)
                                .build())) {

            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return new FileContentResponse(path, content);
        } catch (Exception e) {
            log.error("Failed to read file: {}/{}", projectId, path, e);
            throw new RuntimeException("Failed to read file content", e);
        }
    }

    @Override
    public void saveFile(Long projectId, String filePath, String fileContent) {
        log.info("Saving file for projectId: {}, filePath: {}", projectId, filePath);

        Project project=projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFoundException("Project", projectId.toString()));

        String cleanPath = filePath.startsWith("/") ? filePath.substring(1) : filePath;
        String objectKey = projectId + "/" + cleanPath;
        try {
            byte[] contentBytes = fileContent.getBytes(StandardCharsets.UTF_8);
            try(InputStream inputStream = new ByteArrayInputStream(contentBytes)) {
                // saving the file content
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(projectBucket)
                                .object(objectKey)
                                .stream(inputStream, (long) contentBytes.length, (long) -1)
                                .contentType(determineContentType(filePath))
                                .build());
            }
            // Saving the metaData
            ProjectFile file = projectFileRepository.findByProjectIdAndPath(projectId, cleanPath)
                    .orElseGet(() -> ProjectFile.builder()
                            .project(project)
                            .path(cleanPath)
                            .minioObjectKey(objectKey) // Use the key we generated
                            .createdAt(Instant.now())
                            .build());

            file.setUpdatedAt(Instant.now());
            projectFileRepository.save(file);
            log.info("Saved file: {}", objectKey);
        } catch (Exception e) {
            log.error("Failed to save file {}/{}", projectId, cleanPath, e);
            throw new RuntimeException("File save failed", e);
        }

    }
    private String determineContentType(String path) {
        String type = URLConnection.guessContentTypeFromName(path);
        if (type != null) return type;
        if (path.endsWith(".jsx") || path.endsWith(".ts") || path.endsWith(".tsx")) return "text/javascript";
        if (path.endsWith(".json")) return "application/json";
        if (path.endsWith(".css")) return "text/css";

        return "text/plain";
    }
}
