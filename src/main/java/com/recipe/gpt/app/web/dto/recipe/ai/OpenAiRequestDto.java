package com.recipe.gpt.app.web.dto.recipe.ai;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OpenAiRequestDto {

    @NotEmpty
    private List<String> ingredients;

    @NotEmpty
    private List<String> seasonings;

}
