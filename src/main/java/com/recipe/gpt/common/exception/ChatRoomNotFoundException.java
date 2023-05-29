package com.recipe.gpt.common.exception;

import org.springframework.http.HttpStatus;

public class ChatRoomNotFoundException extends GeneralHttpException {

    public ChatRoomNotFoundException() {
        super(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다.", null);
    }

}