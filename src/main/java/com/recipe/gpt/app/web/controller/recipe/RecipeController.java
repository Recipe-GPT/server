package com.recipe.gpt.app.web.controller.recipe;

import com.recipe.gpt.app.domain.recipe.OpenAIService;
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

@Tag(name = "레시피")
@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final OpenAIService openAIService;

    @Operation(summary = "레시피 질문")
    @PostMapping(ApiPath.LOGIN_OAUTH2)
    public TokenDto recipeQuery(@Validated @RequestBody OAuth2RequestDto body) {
        return null;
    }

}
