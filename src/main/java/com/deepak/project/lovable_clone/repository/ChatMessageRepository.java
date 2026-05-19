package com.deepak.project.lovable_clone.repository;

import com.deepak.project.lovable_clone.entity.ChatMessage;
import com.deepak.project.lovable_clone.entity.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatSession(ChatSession chatSession);
}