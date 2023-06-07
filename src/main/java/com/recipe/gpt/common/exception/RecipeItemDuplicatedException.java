package com.recipe.gpt.common.exception;

import org.springframework.http.HttpStatus;

public class RecipeItemDuplicatedException extends GeneralHttpException {

    public RecipeItemDuplicatedException() {
        super(HttpStatus.BAD_REQUEST, "재료 또는 조미료는 중복될 수 없습니다.", null);
    }

}