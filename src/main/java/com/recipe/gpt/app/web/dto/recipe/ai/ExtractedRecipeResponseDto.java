package com.recipe.gpt.app.web.dto.recipe.ai;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ExtractedRecipeResponseDto {

    private List<ItemResponseDto> ingredients;

    private List<ItemResponseDto> seasonings;

    private List<String> recipe;

    public static ExtractedRecipeResponseDto of(AiServerRecipeResponseDto aiServerRecipeResponse) {
        return new ExtractedRecipeResponseDto(
            ItemResponseDto.listOf(aiServerRecipeResponse.getIngredients()),
            ItemResponseDto.listOf(aiServerRecipeResponse.getSeasonings()),
            aiServerRecipeResponse.getRecipe()
        );
    }

}
