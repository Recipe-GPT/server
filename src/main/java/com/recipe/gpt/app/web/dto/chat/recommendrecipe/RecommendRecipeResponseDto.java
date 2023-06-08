package com.recipe.gpt.app.web.dto.chat.recommendrecipe;

import com.recipe.gpt.app.domain.chat.recommendrecipe.RecommendRecipe;
import com.recipe.gpt.app.domain.chat.recommendrecipe.RecommendRecipeItem;
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

    private String name;

    private String description;

    private static RecommendRecipeResponseDto of(RecommendRecipeItem recommendRecipeItem) {
        return new RecommendRecipeResponseDto(
            recommendRecipeItem.getName(),
            recommendRecipeItem.getDescription()
        );
    }

    public static List<RecommendRecipeResponseDto> listOf(RecommendRecipe recommendRecipe) {
        return recommendRecipe.getRecommendRecipeItems().stream()
            .map(RecommendRecipeResponseDto::of)
            .collect(Collectors.toList());
    }

}
