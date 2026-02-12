package com.deepak.project.lovable_clone.entity;

import com.deepak.project.lovable_clone.enums.MessageRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ChatMessage {

    Long id;
    ChatSession chatSession;
    MessageRole messageRole;
//    Project project;
//    User user;
    MessageRole role;
    String content;
    String toolCalls;  // JSON Array of Tools Called
//    String toolCallId;
    Integer tokensUsed;
    Instant createdAt;
}
