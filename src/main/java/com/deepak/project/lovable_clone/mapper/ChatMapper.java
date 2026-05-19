package com.deepak.project.lovable_clone.mapper;

import com.deepak.project.lovable_clone.dto.chat.ChatResponse;
import com.deepak.project.lovable_clone.entity.ChatMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    List<ChatResponse> toChatResponseList(List<ChatMessage> chatMessageList);

}
