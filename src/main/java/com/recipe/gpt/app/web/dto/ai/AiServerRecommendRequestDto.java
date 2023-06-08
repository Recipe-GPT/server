package com.recipe.gpt.app.web.dto.ai;

import com.recipe.gpt.app.domain.chat.requested.ingredient.RequestedIngredientItem;
import com.recipe.gpt.app.domain.chat.requested.seasoning.RequestedSeasoningItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    @Size(min = 3, max = 20)
    private List<@NotBlank @Size(max = 10) String> ingredients;

    @NotEmpty
    @Size(min = 3, max = 20)
    private List<@NotBlank @Size(max = 10) String> seasonings;

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
