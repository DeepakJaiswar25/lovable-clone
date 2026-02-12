package com.deepak.project.lovable_clone.service;

import com.deepak.project.lovable_clone.dto.project.ProjectResponse;

public interface ProjectService {

    ProjectResponse getProjectById(Long id, Long userId);
}
