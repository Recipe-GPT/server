package com.recipe.gpt.app.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class OAuth2RequestDto {

    @NotEmpty
    @Schema(description = "OAuth2 인증 코드")
    private String code;

}