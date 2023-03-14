package com.recipe.gpt.web.domain.auth.controller.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class OAuth2Rq {

    @NotEmpty
    @Schema(description = "OAuth2 인증 코드")
    private String code;

}