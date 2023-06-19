package com.recipe.gpt.common.exception;

import org.springframework.http.HttpStatus;

public class OAuthException extends GeneralHttpException {

    public OAuthException(String message) {
        super(HttpStatus.UNAUTHORIZED, message, null);
    }

}