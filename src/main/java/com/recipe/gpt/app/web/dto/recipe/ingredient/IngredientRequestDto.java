package com.recipe.gpt.app.web.dto.recipe.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class IngredientRequestDto {

    @NotBlank(message = "재료는 공백일 수 없습니다")
    @Size(max = 20, message = "재료는 최대 20글자여야 합니다")
    private String name;

    @NotBlank
    @Size(max = 10)
    private String quantity;

}
