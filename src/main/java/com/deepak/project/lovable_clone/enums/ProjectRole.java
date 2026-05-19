package com.deepak.project.lovable_clone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.deepak.project.lovable_clone.enums.ProjectPermissions.*;

@Getter
@RequiredArgsConstructor
public enum ProjectRole {
    EDITOR(VIEW, VIEW_MEMBERS, EDIT, DELETE),
    VIEWER(VIEW, VIEW_MEMBERS),
    OWNER(VIEW, VIEW_MEMBERS, MANAGE_MEMBERS, DELETE, EDIT);

    private final Set<ProjectPermissions> permissions;

    ProjectRole(ProjectPermissions... permissions) {
        this.permissions = Set.of(permissions);
    }
}
