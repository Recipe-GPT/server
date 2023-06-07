package com.recipe.gpt.app.domain.chat.recommendrecipe;

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
public class RecommendRecipe {

    @OneToMany(mappedBy = "chat", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<RecommendRecipeItem> recommendRecipe = new ArrayList<>();

    public static RecommendRecipe empty() {
        return new RecommendRecipe();
    }

    public void clear() {
        recommendRecipe.clear();
    }

    public void addRecommendRecipeItem(RecommendRecipeItem recommendRecipeItem) {
        recommendRecipe.add(recommendRecipeItem);
    }

}