package com.deepak.project.lovable_clone.controllers;


import com.deepak.project.lovable_clone.dto.member.InviteMemberRequest;
import com.deepak.project.lovable_clone.dto.member.MemberResponse;
import com.deepak.project.lovable_clone.entity.ProjectMember;
import com.deepak.project.lovable_clone.service.ProjectMemberService;
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
    public ResponseEntity<List<ProjectMember>> getAllProjectMembers(@PathVariable Long projectId) {

        Long userId = 1L;
        return ResponseEntity.ok(projectMemberService.getProjectMembers(userId,projectId));

    }

    @PostMapping
    public ResponseEntity<MemberResponse> inviteMembers(@RequestBody InviteMemberRequest inviteMemberRequest,@PathVariable Long projectId) {
        Long userId = 1L;
        return ResponseEntity.status(HttpStatus.CREATED).body(projectMemberService.inviteMembers(userId,inviteMemberRequest,projectId));
    }


    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMemberRole(
            @PathVariable String projectId, @PathVariable String memberId,@RequestBody InviteMemberRequest inviteMemberRequest
    ){
        Long userId = 1L;
        return ResponseEntity.ok(projectMemberService.updateMemberRole(projectId, memberId, inviteMemberRequest, userId));
    }



    @DeleteMapping("/{memberId}")
    public ResponseEntity<MemberResponse> deleteMember(@PathVariable String projectId, @PathVariable String memberId){
        Long userId = 1L;
        projectMemberService.deleteProjectMember(projectId,memberId,userId);
        return ResponseEntity.noContent().build();
    }

}
