package com.deepak.project.lovable_clone.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectRequest(
       @NotBlank String name
) {
}
