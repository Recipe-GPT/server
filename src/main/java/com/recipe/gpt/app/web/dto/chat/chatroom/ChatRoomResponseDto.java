package com.recipe.gpt.app.web.dto.chat.chatroom;

import com.recipe.gpt.app.domain.chat.chatroom.ChatRoom;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ChatRoomResponseDto {

    private long id;

    private String name;

    public static ChatRoomResponseDto of(ChatRoom chatRoom) {
        return new ChatRoomResponseDto(
            chatRoom.getId(),
            chatRoom.getName()
        );
    }

    public static List<ChatRoomResponseDto> listOf(List<ChatRoom> chatRooms) {
        return chatRooms.stream()
            .map(ChatRoomResponseDto::of)
            .collect(Collectors.toList());
    }

}
