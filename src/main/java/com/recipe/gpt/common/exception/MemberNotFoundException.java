package com.recipe.gpt.common.exception;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends GeneralHttpException {

    public MemberNotFoundException() {
        super(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다.", null);
    }

}