package com.deepak.project.lovable_clone.service;

import com.deepak.project.lovable_clone.dto.auth.AuthResponse;
import com.deepak.project.lovable_clone.dto.auth.LoginRequest;
import com.deepak.project.lovable_clone.dto.auth.SignupRequest;

public interface AuthService {

    AuthResponse signup(SignupRequest signupRequest);

    AuthResponse login(LoginRequest loginRequest);
}
