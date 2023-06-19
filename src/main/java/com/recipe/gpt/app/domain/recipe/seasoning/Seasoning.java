package com.recipe.gpt.app.domain.recipe.seasoning;

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
public class Seasoning {

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<SeasoningItem> seasoning = new ArrayList<>();

    public static Seasoning empty() {
        return new Seasoning();
    }

    public void clear() {
        seasoning.clear();
    }

    public List<SeasoningItem> getSeasoningItems() {
        return seasoning;
    }

    public void addSeasoningItem(SeasoningItem seasoningItem) {
        if (isContainSeasoningItem(seasoningItem)) {
            throw new RecipeItemDuplicatedException();
        }

        seasoning.add(seasoningItem);
    }

    private boolean isContainSeasoningItem(SeasoningItem seasoningItem) {
        return seasoning.stream()
            .anyMatch(item -> item.isSameSeasoningWith(seasoningItem));
    }

}