package com.deepak.project.lovable_clone.service.impl;

import com.deepak.project.lovable_clone.dto.project.ProjectRequest;
import com.deepak.project.lovable_clone.dto.project.ProjectResponse;
import com.deepak.project.lovable_clone.dto.project.ProjectSummaryResponse;
import com.deepak.project.lovable_clone.entity.Project;
import com.deepak.project.lovable_clone.entity.User;
import com.deepak.project.lovable_clone.error.ResourceNotFoundException;
import com.deepak.project.lovable_clone.mapper.ProjectMapper;
import com.deepak.project.lovable_clone.repository.ProjectRepository;
import com.deepak.project.lovable_clone.repository.UserRepository;
import com.deepak.project.lovable_clone.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class ProjectServiceImpl implements ProjectService {

    ProjectMapper projectMapper;
    ProjectRepository projectRepository;
    UserRepository userRepository;

    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {
        List<Project> projects= projectRepository.findByUserId(userId);
       return  projects.stream()
                .map(project -> projectMapper.ProjectToProjectSummaryResponse(project))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse createProject(Long userId, ProjectRequest projectRequest) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User",userId.toString()));
        Project project=Project.builder()
                .name(projectRequest.name())
                .isPublic(false)
                .build();
        project= projectRepository.save(project);
        return projectMapper.ProjectToProjectResponse(project);
    }

    @Override
    public void softDelete(Long userId, Long id) {
       Project project= getAccessibleProjectById(id, userId);
       project.setDeletedAt(Instant.now());
       projectRepository.save(project);
    }

    @Override
    public ProjectResponse updateProject(Long id, Long userId, ProjectRequest projectRequest) {
        Project project= getAccessibleProjectById(id, userId);
        project.setName(projectRequest.name());
        projectRepository.save(project);
        return projectMapper.ProjectToProjectResponse(project);
    }

    @Override
    public ProjectResponse getProjectById(Long id, Long userId) {
        Project project = getAccessibleProjectById(id, userId);
        return projectMapper.ProjectToProjectResponse(project);
    }

    //Internal Function
    public Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectById(projectId,userId).orElseThrow();
    }
}
