package com.deepak.project.lovable_clone.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;


@FieldDefaults(level =  AccessLevel.PRIVATE)
@Getter
@Setter
public class User {

    Long id;
    String email;
    String passwordHash;
    String name;
    String avatarUrl;
    Instant createdAt;
    Instant updatedAt;
    Instant deletedAt;

}
