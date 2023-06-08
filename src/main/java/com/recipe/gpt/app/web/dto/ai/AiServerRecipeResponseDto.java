package com.recipe.gpt.app.web.dto.ai;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AiServerRecipeResponseDto {

    private List<String> ingredients;

    private List<String> seasonings;

    private List<String> recipe;

}
