package com.deepak.project.lovable_clone.service;

import com.deepak.project.lovable_clone.dto.project.ProjectRequest;
import com.deepak.project.lovable_clone.dto.project.ProjectResponse;

import java.util.List;

public interface ProjectService {


    List<ProjectResponse> getUserProjects(Long userId);

    ProjectResponse createProject(Long userId, ProjectRequest projectRequest);

    void softDelete(Long userId, Long id);

    ProjectResponse updateProject(Long id, Long userId, ProjectRequest projectRequest);

    ProjectResponse getprojectById(Long id, Long userId);
}
