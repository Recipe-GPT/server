package com.recipe.gpt.app.web.dto.chat.chatroom;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ChatRoomIdResponseDto {

    private Long chatRoomId;

    public static ChatRoomIdResponseDto of(Long chatRoomId) {
        return new ChatRoomIdResponseDto(chatRoomId);
    }

}
