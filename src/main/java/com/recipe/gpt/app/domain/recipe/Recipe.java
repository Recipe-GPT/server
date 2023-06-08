package com.recipe.gpt.app.domain.recipe;

import com.recipe.gpt.app.domain.chat.Chat;
import com.recipe.gpt.app.domain.recipe.ingredient.Ingredient;
import com.recipe.gpt.app.domain.recipe.procedure.Procedure;
import com.recipe.gpt.app.domain.recipe.seasoning.Seasoning;
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
    private Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        chat.addRecipe(this);
    }

}
