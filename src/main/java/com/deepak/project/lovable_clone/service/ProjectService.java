package com.deepak.project.lovable_clone.service;

import com.deepak.project.lovable_clone.dto.project.ProjectRequest;
import com.deepak.project.lovable_clone.dto.project.ProjectResponse;
import com.deepak.project.lovable_clone.dto.project.ProjectSummaryResponse;

import java.util.List;

public interface ProjectService {


    List<ProjectSummaryResponse> getUserProjects();

    ProjectResponse createProject(ProjectRequest projectRequest);

    void softDelete(Long id);

    ProjectResponse updateProject(Long id, ProjectRequest projectRequest);

    ProjectResponse getProjectById(Long id);
}
