package com.recipe.gpt.app.web.dto.recipe.ai;

import com.recipe.gpt.app.domain.chat.recommendrecipe.RecommendRecipeItem;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AiServerRecommendResponseDto {

    private String name;

    private String description;

    private List<String> ingredients;

    private List<String> seasonings;

    public RecommendRecipeItem toRecommendRecipeItem() {
        return RecommendRecipeItem.builder()
            .name(name)
            .description(description)
            .build();
    }

}