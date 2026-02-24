package com.deepak.project.lovable_clone.dto.member;

import com.deepak.project.lovable_clone.enums.ProjectRole;

public record UpdateMemberRoleRequest(
        ProjectRole role
) {
}
