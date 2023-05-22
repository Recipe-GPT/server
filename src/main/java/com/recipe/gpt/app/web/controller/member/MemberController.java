package com.recipe.gpt.app.web.controller.member;

import com.recipe.gpt.app.web.dto.user.OAuth2RequestDto;
import com.recipe.gpt.app.web.dto.common.TokenDto;
import com.recipe.gpt.app.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증")
@RestController
@RequiredArgsConstructor
public class MemberController {

    @Operation(summary = "OAuth 로그인")
    @PostMapping(ApiPath.LOGIN_OAUTH2)
    public TokenDto loginOAuth2(@Validated @RequestBody OAuth2RequestDto body) {
        return null;
    }


}
