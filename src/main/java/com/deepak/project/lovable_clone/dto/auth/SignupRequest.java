package com.deepak.project.lovable_clone.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
        @NotBlank String name, @Email @NotBlank String username, @NotBlank String Password
) {
}
