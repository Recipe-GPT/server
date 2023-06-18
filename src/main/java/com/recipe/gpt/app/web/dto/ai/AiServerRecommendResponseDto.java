package com.recipe.gpt.app.web.dto.ai;

import com.recipe.gpt.app.domain.recipe.Recipe;
import com.recipe.gpt.app.domain.recipe.ingredient.IngredientItem;
import com.recipe.gpt.app.domain.recipe.seasoning.SeasoningItem;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AiServerRecommendResponseDto {

    private Long recipeId;

    private String name;

    private String description;

    private List<String> ingredients;

    private List<String> seasonings;

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Recipe toRecipe() {
        return Recipe.builder()
            .name(name)
            .description(description)
            .isSelected(false)
            .build();
    }

    public List<IngredientItem> toIngredientItems() {
        List<IngredientItem> ingredientItems = new ArrayList<>();
        for (String ingredient : ingredients) {
            IngredientItem item = IngredientItem.builder()
                .name(ingredient)
                .build();
            ingredientItems.add(item);
        }
        return ingredientItems;
    }

    public List<SeasoningItem> toSeasoningItems() {
        List<SeasoningItem> seasoningItems = new ArrayList<>();
        for (String seasoning : seasonings) {
            SeasoningItem item = SeasoningItem.builder()
                .name(seasoning)
                .build();
            seasoningItems.add(item);
        }
        return seasoningItems;
    }

}