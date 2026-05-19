package com.deepak.project.lovable_clone.repository;

import com.deepak.project.lovable_clone.entity.ChatSession;
import com.deepak.project.lovable_clone.entity.ChatSessionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession, ChatSessionId> {
}