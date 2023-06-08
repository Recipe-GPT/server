package com.recipe.gpt.app.domain.recipe.ingredient;

import com.recipe.gpt.app.domain.recipe.Recipe;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class IngredientItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(length = 10)
    private String quantity;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Builder
    private IngredientItem(Recipe recipe, String name, String quantity) {
        this.recipe = recipe;
        this.name = name;
        this.quantity = quantity;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        recipe.getIngredient().addIngredientItem(this);
    }

    public boolean isSameIngredientWith(IngredientItem ingredientItem) {
        return this.name.equals(ingredientItem.name);
    }

}
