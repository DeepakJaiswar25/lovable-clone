package com.deepak.project.lovable_clone.controllers;

import com.deepak.project.lovable_clone.dto.auth.AuthResponse;
import com.deepak.project.lovable_clone.dto.auth.LoginRequest;
import com.deepak.project.lovable_clone.dto.auth.SignupRequest;
import com.deepak.project.lovable_clone.service.AuthService;
import com.deepak.project.lovable_clone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {


    private AuthService authService;
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest signupRequest){

        return ResponseEntity.ok(authService.signup(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){

        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getProfile(){
        Long userId= 1L;
        return ResponseEntity.ok(userService.getProfile(userId));
    }
}
