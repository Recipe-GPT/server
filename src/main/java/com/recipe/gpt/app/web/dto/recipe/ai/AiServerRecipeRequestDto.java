package com.recipe.gpt.app.web.dto.recipe.ai;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AiServerRecipeRequestDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    @Size(min = 3, max = 20)
    private List<@NotBlank @Size(max = 10) String> ingredients;

    @NotEmpty
    @Size(min = 3, max = 20)
    private List<@NotBlank @Size(max = 10) String> seasonings;

}
