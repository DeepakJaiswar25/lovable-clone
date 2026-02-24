package com.deepak.project.lovable_clone.service.impl;

import com.deepak.project.lovable_clone.dto.project.ProjectRequest;
import com.deepak.project.lovable_clone.dto.project.ProjectResponse;
import com.deepak.project.lovable_clone.service.ProjectService;

import java.util.List;

public class ProjectServiceImpl implements ProjectService {
    @Override
    public List<ProjectResponse> getUserProjects(Long userId) {
        return List.of();
    }

    @Override
    public ProjectResponse createProject(Long userId, ProjectRequest projectRequest) {
        return null;
    }

    @Override
    public void softDelete(Long userId, Long id) {

    }

    @Override
    public ProjectResponse updateProject(Long id, Long userId, ProjectRequest projectRequest) {
        return null;
    }

    @Override
    public ProjectResponse getProjectById(Long id, Long userId) {
        return null;
    }
}
