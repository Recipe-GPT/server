package com.recipe.gpt.app.domain.chat.requested.ingredient;

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
public class RequestedIngredient {

    @OneToMany(mappedBy = "chat", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<RequestedIngredientItem> requestedIngredient = new ArrayList<>();

    public static RequestedIngredient empty() {
        return new RequestedIngredient();
    }

    public void clear() {
        requestedIngredient.clear();
    }

    public void addRequestedIngredientItem(RequestedIngredientItem requestedIngredientItem) {
        if (isContainIngredientItem(requestedIngredientItem)) {
            throw new RecipeItemDuplicatedException();
        }

        requestedIngredient.add(requestedIngredientItem);
    }

    private boolean isContainIngredientItem(RequestedIngredientItem requestedIngredientItem) {
        return requestedIngredient.stream()
            .anyMatch(item -> item.isSameIngredientWith(requestedIngredientItem));
    }

}