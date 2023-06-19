package com.recipe.gpt.app.web.dto.ai;

import com.recipe.gpt.app.domain.recipe.ingredient.IngredientItem;
import com.recipe.gpt.app.domain.recipe.procedure.ProcedureItem;
import com.recipe.gpt.app.domain.recipe.seasoning.SeasoningItem;
import com.recipe.gpt.common.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
            removeNumberedListPrefix(aiServerRecipeResponse.getRecipe())
        );
    }

    public List<IngredientItem> toIngredientItems() {
        List<IngredientItem> ingredientItems = new ArrayList<>();
        for (ItemResponseDto ingredient : ingredients) {
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
        for (ItemResponseDto seasoning : seasonings) {
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

    private static List<String> removeNumberedListPrefix(List<String> recipe) {
        return recipe.stream()
            .map(StringUtils::removeNumberedListPrefix)
            .collect(Collectors.toList());
    }

}
