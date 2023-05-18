package com.recipe.gpt.app.web.dto.recipe.ai;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AiServerRequestDto {

    @NotEmpty
    private List<String> ingredients;

    @NotEmpty
    private List<String> seasonings;

}
