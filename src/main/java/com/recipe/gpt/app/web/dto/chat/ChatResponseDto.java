package com.recipe.gpt.app.web.dto.chat;

import com.recipe.gpt.app.domain.chat.Chat;
import com.recipe.gpt.app.domain.chat.requested.ingredient.RequestedIngredient;
import com.recipe.gpt.app.domain.chat.requested.ingredient.RequestedIngredientItem;
import com.recipe.gpt.app.domain.chat.requested.seasoning.RequestedSeasoning;
import com.recipe.gpt.app.domain.chat.requested.seasoning.RequestedSeasoningItem;
import com.recipe.gpt.app.web.dto.recipe.RecipeResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ChatResponseDto {

    private long id;

    private List<String> requestedIngredients;

    private List<String> requestedSeasonings;

    private List<RecipeResponseDto> recommendRecipe;

    private List<RecipeResponseDto> selectedRecipe;

    public static ChatResponseDto of(Chat chat) {
        List<String> requestIngredients = toRequestedIngredients(chat.getRequestedIngredient());
        List<String> requestSeasonings = toRequestedSeasonings(chat.getRequestedSeasoning());

        return new ChatResponseDto(
            chat.getId(),
            requestIngredients,
            requestSeasonings,
            RecipeResponseDto.listOf(chat.getRecipeList()),
            RecipeResponseDto.selectedRecipeListOf(chat.getRecipeList())
        );
    }

    public static List<ChatResponseDto> listOf(List<Chat> chats) {
        return chats.stream()
            .map(ChatResponseDto::of)
            .collect(Collectors.toList());
    }

    public static List<String> toRequestedIngredients(RequestedIngredient requestedIngredient) {
        return requestedIngredient.getRequestedIngredientItems().stream()
            .map(RequestedIngredientItem::getName)
            .collect(Collectors.toList());
    }

    public static List<String> toRequestedSeasonings(RequestedSeasoning requestedSeasoning) {
        return requestedSeasoning.getRequestedSeasoningItems().stream()
            .map(RequestedSeasoningItem::getName)
            .collect(Collectors.toList());
    }

}
