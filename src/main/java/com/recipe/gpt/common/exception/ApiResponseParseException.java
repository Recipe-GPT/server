package com.recipe.gpt.common.exception;

import org.springframework.http.HttpStatus;

public class ApiResponseParseException extends GeneralHttpException {

    public ApiResponseParseException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "JSON 파싱 중 에러가 발생하였습니다.", null);
    }

}

