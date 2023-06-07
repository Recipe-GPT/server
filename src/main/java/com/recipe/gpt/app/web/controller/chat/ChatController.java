package com.recipe.gpt.app.web.controller.chat;

import com.recipe.gpt.app.domain.chat.ChatService;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecipeRequestDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecommendRequestDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecommendResponseDto;
import com.recipe.gpt.app.web.dto.recipe.ai.ExtractedRecipeResponseDto;
import com.recipe.gpt.app.web.path.ApiPath;
import com.recipe.gpt.app.web.response.ListResponse;
import com.recipe.gpt.common.config.security.context.LoginMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AI 채팅")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "요리 추천 질문")
    @PostMapping(ApiPath.QUERY_RECOMMEND)
    public ResponseEntity<ListResponse<AiServerRecommendResponseDto>> recommendQuery(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long chatRoomId,
        @Valid @RequestBody AiServerRecommendRequestDto body
    ) {
        return ResponseEntity.ok(chatService.recommendQuery(loginMember, chatRoomId, body));
    }

    @Operation(summary = "레시피 질문")
    @PostMapping(ApiPath.QUERY_RECIPE)
    public ResponseEntity<ExtractedRecipeResponseDto> recipeQuery(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long chatRoomId,
        @Valid @RequestBody AiServerRecipeRequestDto body
    ) {
        return ResponseEntity.ok(chatService.recipeQuery(loginMember, chatRoomId, body));
    }

}
