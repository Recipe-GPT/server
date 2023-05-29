package com.recipe.gpt.app.web.dto.search;

import com.recipe.gpt.app.domain.search.ingredient.IngredientData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class IngredientDataResponseDto {

    private Long id;

    private String name;

    public static IngredientDataResponseDto of(IngredientData ingredient) {
        return new IngredientDataResponseDto(
                ingredient.getId(),
                ingredient.getName()
        );
    }

}
