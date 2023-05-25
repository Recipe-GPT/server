package com.recipe.gpt.app.domain.chat.chatroom;

import com.recipe.gpt.app.domain.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findChatRoomsByMember(Member member);

}