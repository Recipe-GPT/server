package com.recipe.gpt.common.exception;

import org.springframework.http.HttpStatus;

public class NotPossibleToAccessBoardException extends GeneralHttpException {

    public NotPossibleToAccessBoardException() {
        super(HttpStatus.BAD_REQUEST, "레시피 게시글에 접근할 수 있는 권한이 없습니다.", null);
    }

}