package com.deepak.project.lovable_clone.entity;

import com.deepak.project.lovable_clone.enums.ProjectRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ProjectMember {

    ProjectMemberId id;
    Project project;
    User user;
    ProjectRole role;
    Long invitedBy;
    Instant invitedAt;
    Instant acceptedAt;
}
