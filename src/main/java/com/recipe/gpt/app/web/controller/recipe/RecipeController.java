package com.recipe.gpt.app.web.controller.recipe;

import com.recipe.gpt.app.domain.chat.ChatService;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecipeRequestDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecipeResponseDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecommendRequestDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecommendResponseDto;
import com.recipe.gpt.app.web.path.ApiPath;
import com.recipe.gpt.app.web.response.ListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "레시피")
@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final ChatService chatService;

    @Operation(summary = "요리 추천 질문")
    @PostMapping(ApiPath.QUERY_RECOMMEND)
    public ResponseEntity<ListResponse<AiServerRecommendResponseDto>> recommendQuery(
        @Valid @RequestBody AiServerRecommendRequestDto body
    ) {
        return ResponseEntity.ok(chatService.recommendQuery(body));
    }

    @Operation(summary = "레시피 질문")
    @PostMapping(ApiPath.QUERY_RECIPE)
    public ResponseEntity<AiServerRecipeResponseDto> recipeQuery(
        @Valid @RequestBody AiServerRecipeRequestDto body
    ) {
        return ResponseEntity.ok(chatService.recipeQuery(body));
    }

}
