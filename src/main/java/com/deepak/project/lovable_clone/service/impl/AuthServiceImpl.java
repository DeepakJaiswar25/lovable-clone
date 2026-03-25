package com.deepak.project.lovable_clone.service.impl;

import com.deepak.project.lovable_clone.dto.auth.AuthResponse;
import com.deepak.project.lovable_clone.dto.auth.LoginRequest;
import com.deepak.project.lovable_clone.dto.auth.SignupRequest;
import com.deepak.project.lovable_clone.entity.User;
import com.deepak.project.lovable_clone.error.BadRequestException;
import com.deepak.project.lovable_clone.mapper.UserMapper;
import com.deepak.project.lovable_clone.repository.UserRepository;
import com.deepak.project.lovable_clone.security.AuthUtil;
import com.deepak.project.lovable_clone.security.WebSecurityConfig;
import com.deepak.project.lovable_clone.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    UserMapper userMapper;
    AuthUtil authUtil;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signup(SignupRequest signupRequest) {
        userRepository.findByUsername(signupRequest.username()).ifPresent(user -> {
            throw new BadRequestException("User already exists with username: "+signupRequest.username());
        });
        User user = userMapper.toEntity(signupRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String token = authUtil.generateAccessToken(user);
        return new AuthResponse(token, userMapper.toUserProfileResponse(user));
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );
        User user = (User) authentication.getPrincipal();
        String token = authUtil.generateAccessToken(user);
        return new AuthResponse(token, userMapper.toUserProfileResponse(user));
    }
}
