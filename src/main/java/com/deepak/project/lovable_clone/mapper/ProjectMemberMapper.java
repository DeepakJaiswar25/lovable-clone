package com.deepak.project.lovable_clone.mapper;

import com.deepak.project.lovable_clone.dto.member.MemberResponse;
import com.deepak.project.lovable_clone.entity.ProjectMember;
import com.deepak.project.lovable_clone.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {


    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "email",source = "user.email")
    @Mapping(target = "name",source = "user.name")
   MemberResponse ProjectMemberToMemberResponse(ProjectMember projectMember);

    @Mapping(target = "userId",source = "id")
    @Mapping(target = "role", constant = "OWNER")
    MemberResponse UserToMemberResponse(User user);


}
