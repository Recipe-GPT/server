package com.recipe.gpt.app.web.controller.auth;

import com.recipe.gpt.app.domain.auth.AuthService;
import com.recipe.gpt.app.domain.auth.OAuthClient;
import com.recipe.gpt.app.web.dto.auth.OAuth2LoginRequestDto;
import com.recipe.gpt.app.web.dto.auth.AccessAndRefreshTokenResponseDto;
import com.recipe.gpt.app.web.dto.auth.OAuthMember;
import com.recipe.gpt.app.web.path.ApiPath;
import com.recipe.gpt.common.config.security.context.LoginMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final OAuthClient oAuthClient;
    private final AuthService authService;

    @Operation(summary = "google oauth 로그인")
    @PostMapping(ApiPath.LOGIN_OAUTH2)
    public ResponseEntity<AccessAndRefreshTokenResponseDto> login(
        @Valid @RequestBody OAuth2LoginRequestDto body) {
        OAuthMember oAuthMember = oAuthClient.getOAuthMember(body.getCode());
        return ResponseEntity.ok(authService.generateAccessAndRefreshToken(oAuthMember));
    }

//    @Operation(summary = "액세스토큰 재발급")
//    @PostMapping(ApiPath.REFRESH_TOKEN)
//    public ResponseEntity<AccessTokenResponseDto> generateAccessToken(
//        @Valid @RequestBody TokenRenewalRequestDto body) {
//        return ResponseEntity.ok(authService.generateAccessToken(body));
//    }

    @Operation(summary = "토큰 확인")
    @GetMapping(ApiPath.VALIDATE_TOKEN)
    public ResponseEntity<Void> validateToken(@AuthenticationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok().build();
    }

}
