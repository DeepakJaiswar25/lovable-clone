package com.deepak.project.lovable_clone.service.impl;

import com.deepak.project.lovable_clone.dto.project.ProjectRequest;
import com.deepak.project.lovable_clone.dto.project.ProjectResponse;
import com.deepak.project.lovable_clone.dto.project.ProjectSummaryResponse;
import com.deepak.project.lovable_clone.entity.Project;
import com.deepak.project.lovable_clone.entity.ProjectMember;
import com.deepak.project.lovable_clone.entity.ProjectMemberId;
import com.deepak.project.lovable_clone.entity.User;
import com.deepak.project.lovable_clone.enums.ProjectRole;
import com.deepak.project.lovable_clone.error.BadRequestException;
import com.deepak.project.lovable_clone.error.ResourceNotFoundException;
import com.deepak.project.lovable_clone.mapper.ProjectMapper;
import com.deepak.project.lovable_clone.repository.ProjectMemberRepository;
import com.deepak.project.lovable_clone.repository.ProjectRepository;
import com.deepak.project.lovable_clone.repository.UserRepository;
import com.deepak.project.lovable_clone.security.AuthUtil;
import com.deepak.project.lovable_clone.service.ProjectService;
import com.deepak.project.lovable_clone.service.ProjectTemplateService;
import com.deepak.project.lovable_clone.service.SubscriptionService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
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
    ProjectMemberRepository projectMemberRepository;
    UserRepository userRepository;
    AuthUtil authUtil;
    SubscriptionService subscriptionService;
    ProjectTemplateService  projectTemplateService;

    @Override
    public List<ProjectSummaryResponse> getUserProjects() {
        Long userId= authUtil.getCurrentUserId();
        var projectWithRoles= projectRepository.findAllAccessibleByUser(userId);
       return  projectWithRoles.stream()
                .map(project -> projectMapper.ProjectToProjectSummaryResponse(project.getProject(),project.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        if(!subscriptionService.canCreateNewProject()) {
            throw new BadRequestException("User cannot create a New project with current Plan, Upgrade plan now.");
        }
        Long userId= authUtil.getCurrentUserId();
//        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User",userId.toString()));
        User user = userRepository.getReferenceById(userId);
        Project project=Project.builder()
                .name(projectRequest.name())
                .isPublic(false)
                .build();
        project= projectRepository.save(project);
        ProjectMemberId projectMemberId= new ProjectMemberId(project.getId(),user.getId());
        ProjectMember projectMember = ProjectMember.builder()
                .role(ProjectRole.OWNER)
                .id(projectMemberId)
                .invitedAt(Instant.now())
                .user(user)
                .acceptedAt(Instant.now())
                .project(project)
                .build();

        projectMemberRepository.save(projectMember);
        projectTemplateService.initializeProjectFromTemplate(project.getId());
        return projectMapper.ProjectToProjectResponse(project);
    }

    @Override
    @PreAuthorize("@security.canDeleteProject(#id)")
    public void softDelete(Long id) {
        Long userId= authUtil.getCurrentUserId();
       Project project= getAccessibleProjectById(id, userId);
       project.setDeletedAt(Instant.now());
       projectRepository.save(project);
    }

    @Override
    @PreAuthorize("@security.canEditProject(#id)")
    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest) {
        Long userId= authUtil.getCurrentUserId();
        Project project= getAccessibleProjectById(id, userId);
        project.setName(projectRequest.name());
        projectRepository.save(project);
        return projectMapper.ProjectToProjectResponse(project);
    }

    @Override
    @PreAuthorize("@security.canViewProject(#projectId)")
    public ProjectSummaryResponse getProjectById(Long projectId) {
        Long userId= authUtil.getCurrentUserId();
        var projectWithRole=projectRepository.findAccessibleProjectByIdWithRole(projectId, userId)
                .orElseThrow(() -> new BadRequestException("Project Not Found"));
        return projectMapper.ProjectToProjectSummaryResponse(projectWithRole.getProject(), projectWithRole.getRole());
    }

    //Internal Function
    public Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectById(projectId,userId).orElseThrow();
    }
}
