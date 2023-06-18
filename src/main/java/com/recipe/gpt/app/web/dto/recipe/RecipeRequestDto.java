package com.recipe.gpt.app.web.dto.recipe;

import com.recipe.gpt.app.domain.recipe.Recipe;
import com.recipe.gpt.app.domain.recipe.ingredient.IngredientItem;
import com.recipe.gpt.app.domain.recipe.procedure.ProcedureItem;
import com.recipe.gpt.app.domain.recipe.seasoning.SeasoningItem;
import com.recipe.gpt.app.web.dto.recipe.ingredient.IngredientRequestDto;
import com.recipe.gpt.app.web.dto.recipe.seasoning.SeasoningRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RecipeRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotEmpty
    @Size(max = 20)
    private List<@NotNull IngredientRequestDto> ingredients;

    @NotEmpty
    @Size(max = 20)
    private List<@NotNull SeasoningRequestDto> seasonings;

    @NotEmpty
    @Size(min = 1, max = 20)
    private List<@NotBlank String> recipe;

    public Recipe toRecipe() {
        return Recipe.builder()
            .name(name)
            .description(description)
            .isSelected(true)
            .build();
    }

    public List<IngredientItem> toIngredientItems() {
        List<IngredientItem> ingredientItems = new ArrayList<>();
        for (IngredientRequestDto ingredient : ingredients) {
            IngredientItem item = IngredientItem.builder()
                .name(ingredient.getName())
                .quantity(ingredient.getQuantity())
                .build();
            ingredientItems.add(item);
        }
        return ingredientItems;
    }

    public List<SeasoningItem> toSeasoningItems() {
        List<SeasoningItem> seasoningItems = new ArrayList<>();
        for (SeasoningRequestDto seasoning : seasonings) {
            SeasoningItem item = SeasoningItem.builder()
                .name(seasoning.getName())
                .quantity(seasoning.getQuantity())
                .build();
            seasoningItems.add(item);
        }
        return seasoningItems;
    }

    public List<ProcedureItem> toProcedureItems() {
        List<ProcedureItem> procedureItems = new ArrayList<>();
        for (String recipe : recipe) {
            ProcedureItem item = ProcedureItem.builder()
                .description(recipe)
                .build();
            procedureItems.add(item);
        }
        return procedureItems;
    }

}
