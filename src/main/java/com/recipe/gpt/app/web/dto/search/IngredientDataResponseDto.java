package com.recipe.gpt.app.web.dto.search;

import com.recipe.gpt.app.domain.search.ingredient.IngredientData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class IngredientDataResponseDto {

    @Schema(description = "식재료 고유 id")
    private Long id;

    @Schema(description = "식재료 이름")
    private String name;

    public static IngredientDataResponseDto of(IngredientData ingredient) {
        return new IngredientDataResponseDto(
                ingredient.getId(),
                ingredient.getName()
        );
    }

    public static List<IngredientDataResponseDto> listOf(List<IngredientData> ingredientData) {
        return ingredientData.stream()
                .map(IngredientDataResponseDto::of)
                .collect(Collectors.toList());
    }

}
