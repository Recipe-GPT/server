package com.recipe.gpt.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundRefreshTokenException extends GeneralHttpException {

    public NotFoundRefreshTokenException() {
        super(HttpStatus.NOT_FOUND, "리프레시 토큰이 만료되었거나 사용할 수 없습니다.", null);
    }

}
