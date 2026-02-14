package com.deepak.project.lovable_clone.service;

import com.deepak.project.lovable_clone.dto.project.FileContentResponse;
import com.deepak.project.lovable_clone.dto.project.FileNode;

public interface FileService {
    FileNode getFileTree(Long projectId, Long userId);

    FileContentResponse getFileContent(Long projectId,String path, Long userId);
}
