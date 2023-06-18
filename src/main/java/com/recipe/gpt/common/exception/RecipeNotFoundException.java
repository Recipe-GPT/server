package com.recipe.gpt.common.exception;

import org.springframework.http.HttpStatus;

public class RecipeNotFoundException extends GeneralHttpException {

    public RecipeNotFoundException() {
        super(HttpStatus.NOT_FOUND, "레시피를 찾을 수 없습니다.", null);
    }

}