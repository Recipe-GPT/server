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

    private List<SeasoningResponseDto> seasoning;

    private List<String> recipe;

    public static RecipeResponseDto of(Recipe recipe) {

        // 레시피가 없고 요리 추천 질문만 한 경우
        if (recipe == null) {
            return null;
        }

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

    public static List<RecipeResponseDto> listOf(List<Recipe> recipeList) {
        return recipeList.stream()
            .map(RecipeResponseDto::of)
            .collect(Collectors.toList());
    }

    public static List<String> toProcedures(Procedure procedure) {
        return procedure.getProcedureItems().stream()
            .map(ProcedureItem::getDescription)
            .collect(Collectors.toList());
    }

}
