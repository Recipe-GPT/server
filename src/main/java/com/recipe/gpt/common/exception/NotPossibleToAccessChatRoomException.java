package com.recipe.gpt.common.exception;

import org.springframework.http.HttpStatus;

public class NotPossibleToAccessChatRoomException extends GeneralHttpException {

    public NotPossibleToAccessChatRoomException() {
        super(HttpStatus.BAD_REQUEST, "채팅방에 접근할 수 있는 권한이 없습니다.", null);
    }

}
