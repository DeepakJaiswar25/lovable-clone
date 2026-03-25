package com.deepak.project.lovable_clone.service.impl;

import com.deepak.project.lovable_clone.dto.member.InviteMemberRequest;
import com.deepak.project.lovable_clone.dto.member.MemberResponse;
import com.deepak.project.lovable_clone.dto.member.UpdateMemberRoleRequest;
import com.deepak.project.lovable_clone.entity.Project;
import com.deepak.project.lovable_clone.entity.ProjectMember;
import com.deepak.project.lovable_clone.entity.ProjectMemberId;
import com.deepak.project.lovable_clone.entity.User;
import com.deepak.project.lovable_clone.mapper.ProjectMemberMapper;
import com.deepak.project.lovable_clone.repository.ProjectMemberRepository;
import com.deepak.project.lovable_clone.repository.ProjectRepository;
import com.deepak.project.lovable_clone.repository.UserRepository;
import com.deepak.project.lovable_clone.security.AuthUtil;
import com.deepak.project.lovable_clone.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    ProjectRepository projectRepository;
    ProjectMemberRepository projectMemberRepository;
    UserRepository userRepository;
    ProjectMemberMapper projectMemberMapper;
    AuthUtil authUtil;

    @Override
    @PreAuthorize("@security.canViewProjectMembers(#projectId)")
    public List<MemberResponse> getProjectMembers(Long projectId) {
        Long userId= authUtil.getCurrentUserId();
        Project project =  getAccessibleProjectById(projectId, userId);

          return projectMemberRepository.findByIdProjectId(project.getId())
                        .stream()
                        .map(projectMember -> projectMemberMapper.ProjectMemberToMemberResponse(projectMember))
                        .toList();

    }

    @Override
    @PreAuthorize("@security.canManageProjectMembers(#projectId)")
    public MemberResponse inviteMembers(InviteMemberRequest inviteMemberRequest, Long projectId) {
        Long userId= authUtil.getCurrentUserId();
        Project project= getAccessibleProjectById(projectId, userId);
        User invitee= userRepository.findByUsername(inviteMemberRequest.username()).orElseThrow();
        if(invitee.getId().equals(userId)){
            throw new RuntimeException("Not Allowed to invite yourself");
        }
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId,invitee.getId());
        if(projectMemberRepository.existsById(projectMemberId)){
            throw new RuntimeException("Cannot Invite Again");
        }
        ProjectMember projectMember = ProjectMember
                                        .builder()
                                        .id(projectMemberId)
                                        .role(inviteMemberRequest.role())
                                        .user(invitee)
                                        .project(project)
                                        .invitedAt(Instant.now())
                                        .build();
        projectMemberRepository.save(projectMember);

        return projectMemberMapper.ProjectMemberToMemberResponse(projectMember);
    }

    @Override
    @PreAuthorize("@security.canManageProjectMembers(#projectId)")
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest inviteMemberRequest) {
        Long userId= authUtil.getCurrentUserId();
        Project  project = getAccessibleProjectById(projectId, userId);
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId,memberId);
        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId).orElseThrow();
        projectMember.setRole(inviteMemberRequest.role());
        projectMemberRepository.save(projectMember);
        return projectMemberMapper.ProjectMemberToMemberResponse(projectMember);
    }

    @Override
    public void removeProjectMember(Long projectId, Long memberId) {
        Long userId= authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId,memberId);
        if(!projectMemberRepository.existsById(projectMemberId)){
            throw new RuntimeException("Cannot Remove member");
        }
        projectMemberRepository.deleteById(projectMemberId);

    }

    //Internal Function
    public Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectById(projectId,userId).orElseThrow();
    }
}
