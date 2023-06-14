package com.recipe.gpt.app.web.dto.recipe.seasoning;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SeasoningRequestDto {

    @NotBlank
    @Size(max = 10)
    private String name;

    @NotBlank
    @Size(max = 10)
    private String quantity;

}
