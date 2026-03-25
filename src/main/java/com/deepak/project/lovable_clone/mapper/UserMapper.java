package com.deepak.project.lovable_clone.mapper;

import com.deepak.project.lovable_clone.dto.auth.SignupRequest;
import com.deepak.project.lovable_clone.dto.auth.UserProfileResponse;
import com.deepak.project.lovable_clone.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


    User toEntity(SignupRequest signupRequest);

    UserProfileResponse toUserProfileResponse(User user);
}
