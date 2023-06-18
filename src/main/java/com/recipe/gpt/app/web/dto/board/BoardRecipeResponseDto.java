package com.recipe.gpt.app.web.dto.board;

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
public class BoardRecipeResponseDto {

    private long id;

    private List<IngredientResponseDto> ingredients;

    private List<SeasoningResponseDto> seasonings;

    private List<String> recipe;

    public static BoardRecipeResponseDto of(Recipe recipe) {
        List<String> procedures = toProcedures(recipe.getProcedure());

        return new BoardRecipeResponseDto(
            recipe.getId(),
            IngredientResponseDto.listOf(recipe.getIngredient()),
            SeasoningResponseDto.listOf(recipe.getSeasoning()),
            procedures
        );
    }

    public static List<String> toProcedures(Procedure procedure) {
        return procedure.getProcedureItems().stream()
            .map(ProcedureItem::getDescription)
            .collect(Collectors.toList());
    }

}
