package com.deepak.project.lovable_clone.security;

import com.deepak.project.lovable_clone.enums.ProjectPermissions;
import com.deepak.project.lovable_clone.enums.ProjectRole;
import com.deepak.project.lovable_clone.repository.ProjectMemberRepository;
import com.deepak.project.lovable_clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.deepak.project.lovable_clone.enums.ProjectPermissions.*;

@Component("security")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SecurityExpression {

    AuthUtil authUtil;
    ProjectMemberRepository projectMemberRepository;

    public boolean hasPermissions(Long projectId,ProjectPermissions projectPermissions){
        Long userId= authUtil.getCurrentUserId();
        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId,userId)
                .map(role -> role.getPermissions().contains(projectPermissions))
                .orElse(false);
    }

    public boolean canViewProject(Long projectId) {
        return hasPermissions(projectId,VIEW);
    }

    public boolean canEditProject(Long projectId) {
        return hasPermissions(projectId,EDIT);
    }

    public boolean canDeleteProject(Long projectId) {
        return hasPermissions(projectId,DELETE);
    }

    public boolean canViewProjectMembers(Long projectId) {
        return hasPermissions(projectId,VIEW_MEMBERS);
    }

    public boolean canManageProjectMembers(Long projectId) {
        return hasPermissions(projectId,MANAGE_MEMBERS);
    }
}
