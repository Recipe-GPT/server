package com.recipe.gpt.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundMemberException extends GeneralHttpException {

    public NotFoundMemberException() {
        super(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다.", null);
    }

}