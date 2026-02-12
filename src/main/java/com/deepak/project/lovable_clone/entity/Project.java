package com.deepak.project.lovable_clone.entity;

import java.time.Instant;

public class Project {

    Long id;
    String name;
    User owner;
    Boolean isPublic=false;
    Instant createdAt;
    Instant updatedAt;
    Instant deletedAt;

}
