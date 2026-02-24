package com.deepak.project.lovable_clone.service.impl;

import com.deepak.project.lovable_clone.dto.member.InviteMemberRequest;
import com.deepak.project.lovable_clone.dto.member.MemberResponse;
import com.deepak.project.lovable_clone.dto.member.UpdateMemberRoleRequest;
import com.deepak.project.lovable_clone.entity.ProjectMember;
import com.deepak.project.lovable_clone.service.ProjectMemberService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {
    @Override
    public List<ProjectMember> getProjectMembers(Long userId, Long projectId) {
        return List.of();
    }

    @Override
    public MemberResponse inviteMembers(Long userId, InviteMemberRequest inviteMemberRequest, Long projectId) {
        return null;
    }

    @Override
    public MemberResponse updateMemberRole(String projectId, String memberId, UpdateMemberRoleRequest inviteMemberRequest, Long userId) {
        return null;
    }

    @Override
    public void deleteProjectMember(String projectId, String memberId, Long UserId) {

    }
}
