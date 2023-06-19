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

    @NotBlank(message = "이름은 공백일 수 없습니다")
    @Size(max = 64, message = "이름은 최대 64글자여야 합니다")
    private String name;

    @NotBlank(message = "설명은 공백일 수 없습니다")
    @Size(max = 200, message = "설명은 최대 200글자여야 합니다")
    private String description;

    @Size(min = 3, max = 20, message = "재료의 갯수는 3 ~ 20개여야 합니다")
    private List<@NotNull IngredientRequestDto> ingredients;

    @Size(min = 1, max = 20, message = "양념의 갯수는 1 ~ 20개여야 합니다")
    private List<@NotNull SeasoningRequestDto> seasonings;

    @Size(min = 1, max = 50, message = "레시피의 단계는 1 ~ 50개여야 합니다")
    private List<
        @NotBlank(message = "레시피는 공백일 수 없습니다")
        @Size(max = 200, message = "레시피는 최대 200글자여야 합니다")
        String> recipe;

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
