package com.deepak.project.lovable_clone.controllers;


import com.deepak.project.lovable_clone.dto.member.InviteMemberRequest;
import com.deepak.project.lovable_clone.dto.member.MemberResponse;
import com.deepak.project.lovable_clone.dto.member.UpdateMemberRoleRequest;
import com.deepak.project.lovable_clone.entity.ProjectMember;
import com.deepak.project.lovable_clone.service.ProjectMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/members")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllProjectMembers(@PathVariable Long projectId) {

        Long userId = 1L;
        return ResponseEntity.ok(projectMemberService.getProjectMembers(userId,projectId));

    }

    @PostMapping
    public ResponseEntity<MemberResponse> inviteMembers(@RequestBody @Valid InviteMemberRequest inviteMemberRequest, @PathVariable Long projectId) {
        Long userId = 1L;
        return ResponseEntity.status(HttpStatus.CREATED).body(projectMemberService.inviteMembers(userId,inviteMemberRequest,projectId));
    }


    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMemberRole(
            @PathVariable Long projectId, @PathVariable Long memberId, @RequestBody @Valid UpdateMemberRoleRequest updateMemberRoleRequest
    ){
        Long userId = 1L;
        return ResponseEntity.ok(projectMemberService.updateMemberRole(projectId, memberId, updateMemberRoleRequest, userId));
    }



    @DeleteMapping("/{memberId}")
    public ResponseEntity<MemberResponse> deleteMember(@PathVariable Long projectId, @PathVariable Long memberId){
        Long userId = 1L;
        projectMemberService.removeProjectMember(projectId,memberId,userId);
        return ResponseEntity.noContent().build();
    }

}
