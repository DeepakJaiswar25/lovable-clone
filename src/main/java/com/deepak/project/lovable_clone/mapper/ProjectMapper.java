package com.deepak.project.lovable_clone.mapper;

import com.deepak.project.lovable_clone.dto.project.ProjectRequest;
import com.deepak.project.lovable_clone.dto.project.ProjectResponse;
import com.deepak.project.lovable_clone.dto.project.ProjectSummaryResponse;
import com.deepak.project.lovable_clone.entity.Project;
import com.deepak.project.lovable_clone.enums.ProjectRole;
import jakarta.persistence.ManyToOne;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponse ProjectToProjectResponse(Project project);

    List<ProjectSummaryResponse> ProjectToProjectSummaryResponseList(List<Project> projectList);

    ProjectSummaryResponse ProjectToProjectSummaryResponse(Project project, ProjectRole role);
}
