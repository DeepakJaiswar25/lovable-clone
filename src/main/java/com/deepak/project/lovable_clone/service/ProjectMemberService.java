package com.deepak.project.lovable_clone.service;

import com.deepak.project.lovable_clone.dto.member.InviteMemberRequest;
import com.deepak.project.lovable_clone.dto.member.MemberResponse;
import com.deepak.project.lovable_clone.dto.member.UpdateMemberRoleRequest;
import com.deepak.project.lovable_clone.entity.ProjectMember;

import java.util.List;

public interface ProjectMemberService {


    List<MemberResponse> getProjectMembers(Long userId, Long projectId);

    MemberResponse inviteMembers(Long userId, InviteMemberRequest inviteMemberRequest, Long projectId);

    MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest inviteMemberRequest, Long userId);

    void removeProjectMember(Long projectId, Long memberId,Long UserId);
}
