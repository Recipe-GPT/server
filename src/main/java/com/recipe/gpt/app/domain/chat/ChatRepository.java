package com.recipe.gpt.app.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat, Long> {
}