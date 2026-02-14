package com.deepak.project.lovable_clone.dto.member;

import com.deepak.project.lovable_clone.enums.ProjectRole;

public record InviteMemberRequest(

        String email,
        ProjectRole role
) {
}
