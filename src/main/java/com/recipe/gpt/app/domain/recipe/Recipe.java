package com.recipe.gpt.app.domain.recipe;

import com.recipe.gpt.app.domain.board.Board;
import com.recipe.gpt.app.domain.chat.Chat;
import com.recipe.gpt.app.domain.recipe.ingredient.Ingredient;
import com.recipe.gpt.app.domain.recipe.ingredient.IngredientItem;
import com.recipe.gpt.app.domain.recipe.procedure.Procedure;
import com.recipe.gpt.app.domain.recipe.seasoning.Seasoning;
import com.recipe.gpt.app.domain.recipe.seasoning.SeasoningItem;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Lob
    private String description;

    @Column(nullable = false)
    private Boolean isSelected;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Embedded
    private Ingredient ingredient = Ingredient.empty();

    @Embedded
    private Seasoning seasoning = Seasoning.empty();

    @Embedded
    private Procedure procedure = Procedure.empty();

    @Builder
    private Recipe(String name, String description, Boolean isSelected) {
        this.name = name;
        this.description = description;
        this.isSelected = isSelected;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        chat.addRecipe(this);
    }

    public void setBoard(Board board) {
        board.setRecipe(this);
    }

    public void updateIsSelected() {
        this.isSelected = true;
    }

    public void update(List<IngredientItem> ingredientItems,
        List<SeasoningItem> seasoningItems) {
        updateIngredient(ingredientItems);
        updateSeasoning(seasoningItems);
    }

    private void updateIngredient(List<IngredientItem> ingredientItems) {
        this.ingredient.clear();
        for (IngredientItem ingredientItem : ingredientItems) {
            ingredientItem.setRecipe(this);
        }
    }

    private void updateSeasoning(List<SeasoningItem> seasoningItems) {
        this.seasoning.clear();
        for (SeasoningItem seasoningItem : seasoningItems) {
            seasoningItem.setRecipe(this);
        }
    }

}
