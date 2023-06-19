package com.recipe.gpt.app.web.dto.ai;

import com.recipe.gpt.app.domain.chat.requested.ingredient.RequestedIngredientItem;
import com.recipe.gpt.app.domain.chat.requested.seasoning.RequestedSeasoningItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AiServerRecommendRequestDto {

    @Size(min = 3, max = 20, message = "재료의 갯수는 3 ~ 20개여야 합니다")
    private List<
        @NotBlank(message = "재료는 공백일 수 없습니다")
        @Size(max = 20, message = "재료는 최대 20글자여야 합니다")
        String> ingredients;

    @Size(min = 1, max = 20, message = "양념의 갯수는 1 ~ 20개여야 합니다")
    private List<
        @NotBlank(message = "양념은 공백일 수 없습니다")
        @Size(max = 20, message = "양념은 최대 20글자여야 합니다")
        String> seasonings;

    public List<RequestedIngredientItem> toRequestedIngredientItems() {
        List<RequestedIngredientItem> requestedIngredientItems = new ArrayList<>();
        for (String ingredient : ingredients) {
            RequestedIngredientItem item = RequestedIngredientItem.builder()
                .name(ingredient)
                .build();
            requestedIngredientItems.add(item);
        }
        return requestedIngredientItems;
    }

    public List<RequestedSeasoningItem> toRequestedSeasoningItems() {
        List<RequestedSeasoningItem> requestedSeasoningItems = new ArrayList<>();
        for (String seasoning : seasonings) {
            RequestedSeasoningItem item = RequestedSeasoningItem.builder()
                .name(seasoning)
                .build();
            requestedSeasoningItems.add(item);
        }
        return requestedSeasoningItems;
    }

}
