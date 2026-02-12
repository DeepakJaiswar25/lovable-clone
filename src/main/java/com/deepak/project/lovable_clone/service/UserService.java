package com.deepak.project.lovable_clone.service;

import com.deepak.project.lovable_clone.dto.auth.AuthResponse;

public interface UserService {
    AuthResponse getProfile(Long userId);
}
