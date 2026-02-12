package com.deepak.project.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Plan {
    Long id;
    String name;
    String stripePriceId;
    Integer maxProjects;
    Integer maxTokensPerDay;
    Integer maxPreviews;  //max number of previews allowed per plan
    Boolean unlimitedAi;  //unlimited access to LLM, ignore maxTokensPerDay if true
    Boolean active;
}
