package com.recipe.gpt.app.web.dto.recipe;

import com.recipe.gpt.app.domain.recipe.Recipe;
import com.recipe.gpt.app.domain.recipe.procedure.Procedure;
import com.recipe.gpt.app.domain.recipe.procedure.ProcedureItem;
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
public class RecipeResponseDto {

    private long id;

    private String name;

    private String description;

    private List<IngredientResponseDto> ingredients;

    private List<SeasoningResponseDto> seasonings;

    private List<String> recipe;

    public static RecipeResponseDto of(Recipe recipe) {
        List<String> procedures = toProcedures(recipe.getProcedure());

        return new RecipeResponseDto(
            recipe.getId(),
            recipe.getName(),
            recipe.getDescription(),
            IngredientResponseDto.listOf(recipe.getIngredient()),
            SeasoningResponseDto.listOf(recipe.getSeasoning()),
            procedures
        );
    }

    public static List<RecipeResponseDto> selectedRecipeListOf(List<Recipe> recipeList) {
        return recipeList.stream()
            .filter(Recipe::getIsSelected)
            .map(RecipeResponseDto::of)
            .collect(Collectors.toList());
    }

    public static List<String> toProcedures(Procedure procedure) {
        List<String> procedures = procedure.getProcedureItems().stream()
            .map(ProcedureItem::getDescription)
            .collect(Collectors.toList());

        // 선택되지 않은 레시피라면 null 반환
        return procedures.isEmpty() ? null : procedures;
    }

}
