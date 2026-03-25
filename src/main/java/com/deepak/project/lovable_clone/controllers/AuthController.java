package com.deepak.project.lovable_clone.controllers;

import com.deepak.project.lovable_clone.dto.auth.AuthResponse;
import com.deepak.project.lovable_clone.dto.auth.LoginRequest;
import com.deepak.project.lovable_clone.dto.auth.SignupRequest;
import com.deepak.project.lovable_clone.security.AuthUtil;
import com.deepak.project.lovable_clone.service.AuthService;
import com.deepak.project.lovable_clone.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {


    AuthService authService;
    UserService userService;
    AuthUtil authUtil;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody @Valid SignupRequest signupRequest){

        return ResponseEntity.ok(authService.signup(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest){

        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getProfile(){
        Long userId= authUtil.getCurrentUserId();
        return ResponseEntity.ok(userService.getProfile(userId));
    }
}
