package com.deepak.project.lovable_clone.service;

import com.deepak.project.lovable_clone.dto.member.InviteMemberRequest;
import com.deepak.project.lovable_clone.dto.member.MemberResponse;
import com.deepak.project.lovable_clone.entity.ProjectMember;

import java.util.List;

public interface ProjectMemberService {


    List<ProjectMember> getProjectMembers(Long userId, Long projectId);

    MemberResponse inviteMembers(Long userId, InviteMemberRequest inviteMemberRequest, Long projectId);

    MemberResponse updateMemberRole(String projectId, String memberId, InviteMemberRequest inviteMemberRequest, Long userId);

    void deleteProjectMember(String projectId, String memberId,Long UserId);
}
