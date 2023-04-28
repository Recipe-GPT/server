package com.recipe.gpt.app.web.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;

public class TokenDto {

    @Schema(description = "jwt token")
    private String token;

    @Schema(description = "토큰 만료 시간")
    private String validity;

}
