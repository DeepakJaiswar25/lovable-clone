package com.deepak.project.lovable_clone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.deepak.project.lovable_clone.enums.ProjectPermissions.*;

@Getter
@RequiredArgsConstructor
public enum ProjectRole {
    EDITOR(Set.of(VIEW,VIEW_MEMBERS,EDIT,DELETE)),
    VIEWER(Set.of(VIEW,VIEW_MEMBERS)),
    OWNER(Set.of(VIEW,VIEW_MEMBERS,MANAGE_MEMBERS,DELETE,EDIT));


    private final Set<ProjectPermissions> permissions;
}
