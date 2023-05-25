package com.recipe.gpt.app.web.dto.chat.chatroom;

import com.recipe.gpt.app.domain.chat.chatroom.ChatRoom;
import com.recipe.gpt.app.web.dto.member.MemberResponseDto;
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

    private MemberResponseDto writer;

    public static ChatRoomResponseDto of(ChatRoom chatRoom) {
        return new ChatRoomResponseDto(
            chatRoom.getId(),
            chatRoom.getName(),
            MemberResponseDto.of(chatRoom.getMember())
        );
    }

    public static List<ChatRoomResponseDto> listOf(List<ChatRoom> chatRooms) {
        return chatRooms.stream()
            .map(ChatRoomResponseDto::of)
            .collect(Collectors.toList());
    }

}
