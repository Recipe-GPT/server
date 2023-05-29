package com.recipe.gpt.app.web.dto.chat.chatroom;

import com.recipe.gpt.app.domain.chat.chatroom.ChatRoom;
import com.recipe.gpt.app.domain.member.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ChatRoomRequestDto {

    @NotBlank
    private String name;

    public ChatRoom toChatRoom(Member member) {
        return ChatRoom.builder()
            .name(name)
            .member(member)
            .build();
    }

}
