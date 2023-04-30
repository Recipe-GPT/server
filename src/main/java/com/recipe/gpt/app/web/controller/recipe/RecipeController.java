package com.recipe.gpt.app.web.controller.recipe;

import com.recipe.gpt.app.domain.recipe.RecipeService;
import com.recipe.gpt.app.web.dto.recipe.ai.OpenAiRequestDto;
import com.recipe.gpt.app.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "레시피")
@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @Operation(summary = "레시피 질문")
    @PostMapping(ApiPath.RECIPE_QUERY)
    public ResponseEntity<?> recipeQuery(@RequestBody OpenAiRequestDto body) {
        return recipeService.recipeQuery(body);
    }

}
