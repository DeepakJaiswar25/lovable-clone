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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/members")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllProjectMembers(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectMemberService.getProjectMembers(projectId));

    }

    @PostMapping
    public ResponseEntity<MemberResponse> inviteMembers(@RequestBody @Valid InviteMemberRequest inviteMemberRequest, @PathVariable Long projectId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectMemberService.inviteMembers(inviteMemberRequest,projectId));
    }


    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMemberRole(
            @PathVariable Long projectId, @PathVariable Long memberId, @RequestBody @Valid UpdateMemberRoleRequest updateMemberRoleRequest
    ){

        return ResponseEntity.ok(projectMemberService.updateMemberRole(projectId, memberId, updateMemberRoleRequest));
    }



    @DeleteMapping("/{memberId}")
    public ResponseEntity<MemberResponse> deleteMember(@PathVariable Long projectId, @PathVariable Long memberId){

        projectMemberService.removeProjectMember(projectId,memberId);
        return ResponseEntity.noContent().build();
    }

}
