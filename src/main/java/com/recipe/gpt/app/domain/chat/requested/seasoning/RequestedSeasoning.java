package com.recipe.gpt.app.domain.chat.requested.seasoning;

import com.recipe.gpt.common.exception.RecipeItemDuplicatedException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestedSeasoning {

    @OneToMany(mappedBy = "chat", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<RequestedSeasoningItem> requestedSeasoning = new ArrayList<>();

    public static RequestedSeasoning empty() {
        return new RequestedSeasoning();
    }

    public List<RequestedSeasoningItem> getRequestedSeasoningItems() {
        return requestedSeasoning;
    }

    public void addRequestedSeasoningItem(RequestedSeasoningItem requestedSeasoningItem) {
        if (isContainSeasoningItem(requestedSeasoningItem)) {
            throw new RecipeItemDuplicatedException();
        }

        requestedSeasoning.add(requestedSeasoningItem);
    }

    private boolean isContainSeasoningItem(RequestedSeasoningItem requestedSeasoningItem) {
        return requestedSeasoning.stream()
            .anyMatch(item -> item.isSameSeasoningWith(requestedSeasoningItem));
    }

}