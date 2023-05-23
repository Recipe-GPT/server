package com.recipe.gpt.app.web.controller.recipe;

import com.recipe.gpt.app.domain.chat.ChatService;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRequestDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerResponseDto;
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

    @Operation(summary = "레시피 질문")
    @PostMapping(ApiPath.RECIPE_QUERY)
    public ResponseEntity<ListResponse<AiServerResponseDto>> recipeQuery(
        @Valid @RequestBody AiServerRequestDto body
    ) {
        return ResponseEntity.ok(chatService.recipeQuery(body));
    }

}
