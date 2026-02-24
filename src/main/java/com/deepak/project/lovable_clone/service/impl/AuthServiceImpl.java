package com.deepak.project.lovable_clone.service.impl;

import com.deepak.project.lovable_clone.dto.auth.AuthResponse;
import com.deepak.project.lovable_clone.dto.auth.LoginRequest;
import com.deepak.project.lovable_clone.dto.auth.SignupRequest;
import com.deepak.project.lovable_clone.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public AuthResponse signup(SignupRequest signupRequest) {
        return null;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        return null;
    }
}
