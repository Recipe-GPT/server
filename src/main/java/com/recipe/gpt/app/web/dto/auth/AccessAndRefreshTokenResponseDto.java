package com.recipe.gpt.app.web.dto.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AccessAndRefreshTokenResponseDto {

    private String accessToken;

    private String refreshToken;

    private String accessTokenValidity;

    private String refreshTokenValidity;

}
