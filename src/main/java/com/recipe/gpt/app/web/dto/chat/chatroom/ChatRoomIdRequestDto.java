package com.recipe.gpt.app.web.dto.chat.chatroom;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ChatRoomIdRequestDto {

    @NotNull
    private Long chatRoomId;

}
