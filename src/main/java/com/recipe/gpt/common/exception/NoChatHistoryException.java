package com.recipe.gpt.common.exception;

import org.springframework.http.HttpStatus;

public class NoChatHistoryException extends GeneralHttpException {

    public NoChatHistoryException() {
        super(HttpStatus.NOT_FOUND, "지난 채팅 기록이 없습니다.", null);
    }

}