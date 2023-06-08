package com.recipe.gpt.app.web.dto.recipe.ingredient;

import com.recipe.gpt.app.domain.recipe.ingredient.Ingredient;
import com.recipe.gpt.app.domain.recipe.ingredient.IngredientItem;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class IngredientResponseDto {

    private String name;

    private String quantity;

    private static IngredientResponseDto of(IngredientItem ingredientItem) {
        return new IngredientResponseDto(
            ingredientItem.getName(),
            ingredientItem.getQuantity()
        );
    }

    public static List<IngredientResponseDto> listOf(Ingredient ingredient) {
        return ingredient.getIngredientItems().stream()
            .map(IngredientResponseDto::of)
            .collect(Collectors.toList());
    }

}
