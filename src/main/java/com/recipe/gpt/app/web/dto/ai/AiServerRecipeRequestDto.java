package com.recipe.gpt.app.web.dto.ai;

import com.recipe.gpt.app.domain.recipe.Recipe;
import com.recipe.gpt.app.domain.recipe.ingredient.Ingredient;
import com.recipe.gpt.app.domain.recipe.ingredient.IngredientItem;
import com.recipe.gpt.app.domain.recipe.seasoning.Seasoning;
import com.recipe.gpt.app.domain.recipe.seasoning.SeasoningItem;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "이름은 공백일 수 없습니다")
    private String name;

    @NotBlank(message = "이름은 공백일 수 없습니다")
    private String description;

    @Size(min = 3, max = 20, message = "재료의 갯수는 3 ~ 20개여야 합니다")
    private List<
        @NotBlank(message = "재료는 공백일 수 없습니다")
        @Size(max = 20, message = "재료는 최대 20글자여야 합니다")
        String> ingredients;

    @Size(min = 2, max = 20, message = "양념의 갯수는 2 ~ 20개여야 합니다")
    private List<
        @NotBlank(message = "양념은 공백일 수 없습니다")
        @Size(max = 20, message = "양념은 최대 20글자여야 합니다")
        String> seasonings;

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

}
