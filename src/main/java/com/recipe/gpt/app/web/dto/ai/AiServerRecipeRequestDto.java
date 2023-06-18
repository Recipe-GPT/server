package com.recipe.gpt.app.web.dto.ai;

import com.recipe.gpt.app.domain.recipe.Recipe;
import com.recipe.gpt.app.domain.recipe.ingredient.Ingredient;
import com.recipe.gpt.app.domain.recipe.ingredient.IngredientItem;
import com.recipe.gpt.app.domain.recipe.seasoning.Seasoning;
import com.recipe.gpt.app.domain.recipe.seasoning.SeasoningItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AiServerRecipeRequestDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    @Size(min = 3, max = 20)
    private List<@NotBlank @Size(max = 10) String> ingredients;

    @NotEmpty
    @Size(min = 3, max = 20)
    private List<@NotBlank @Size(max = 10) String> seasonings;

    public static AiServerRecipeRequestDto of(Recipe recipe) {
        return new AiServerRecipeRequestDto(
            recipe.getName(),
            recipe.getDescription(),
            toIngredients(recipe.getIngredient()),
            toSeasonings(recipe.getSeasoning())
        );
    }

    public static List<String> toIngredients(Ingredient ingredient) {
        return ingredient.getIngredientItems().stream()
            .map(IngredientItem::getName)
            .collect(Collectors.toList());
    }

    public static List<String> toSeasonings(Seasoning seasoning) {
        return seasoning.getSeasoningItems().stream()
            .map(SeasoningItem::getName)
            .collect(Collectors.toList());
    }

    public Recipe toRecipe() {
        return Recipe.builder()
            .name(name)
            .description(description)
            .isSelected(true)
            .build();
    }

}
