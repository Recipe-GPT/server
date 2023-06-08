package com.recipe.gpt.app.domain.chat;

import com.recipe.gpt.app.domain.chat.chatroom.ChatRoom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findFirstByChatRoomOrderByIdDesc(ChatRoom chatRoom);

}