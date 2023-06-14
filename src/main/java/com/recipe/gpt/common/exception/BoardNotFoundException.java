package com.recipe.gpt.common.exception;

import org.springframework.http.HttpStatus;

public class BoardNotFoundException extends GeneralHttpException {

    public BoardNotFoundException() {
        super(HttpStatus.NOT_FOUND, "레시피 게시글을 찾을 수 없습니다.", null);
    }

}