package com.recipe.gpt.app.web.dto.recipe;

import com.recipe.gpt.app.domain.recipe.Recipe;
import com.recipe.gpt.app.web.dto.recipe.ingredient.IngredientResponseDto;
import com.recipe.gpt.app.web.dto.recipe.seasoning.SeasoningResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RecommendRecipeResponseDto {

    private long id;

    private String name;

    private String description;

    private List<IngredientResponseDto> ingredients;

    private List<SeasoningResponseDto> seasonings;

    public static RecommendRecipeResponseDto of(Recipe recipe) {
        return new RecommendRecipeResponseDto(
            recipe.getId(),
            recipe.getName(),
            recipe.getDescription(),
            IngredientResponseDto.listOf(recipe.getIngredient()),
            SeasoningResponseDto.listOf(recipe.getSeasoning())
        );
    }

    public static List<RecommendRecipeResponseDto> listOf(List<Recipe> recipeList) {
        return recipeList.stream()
            .map(RecommendRecipeResponseDto::of)
            .collect(Collectors.toList());
    }

}