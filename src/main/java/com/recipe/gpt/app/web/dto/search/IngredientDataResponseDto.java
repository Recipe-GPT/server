package com.recipe.gpt.app.web.dto.search;

import com.recipe.gpt.app.domain.search.ingredient.IngredientData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}