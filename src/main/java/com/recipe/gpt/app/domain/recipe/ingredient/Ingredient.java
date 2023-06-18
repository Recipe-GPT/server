package com.recipe.gpt.app.domain.recipe.ingredient;

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
public class Ingredient {

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<IngredientItem> ingredient = new ArrayList<>();

    public static Ingredient empty() {
        return new Ingredient();
    }

    public List<IngredientItem> getIngredientItems() {
        return ingredient;
    }

    public void addIngredientItem(IngredientItem ingredientItem) {
        if (isContainIngredientItem(ingredientItem)) {
            throw new RecipeItemDuplicatedException();
        }

        ingredient.add(ingredientItem);
    }

    private boolean isContainIngredientItem(IngredientItem ingredientItem) {
        return ingredient.stream()
            .anyMatch(item -> item.isSameIngredientWith(ingredientItem));
    }

    public void clear() {
        ingredient.clear();
    }

}