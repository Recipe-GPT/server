package com.recipe.gpt.app.domain.chat.recommendrecipe;

import com.recipe.gpt.app.domain.chat.Chat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RecommendRecipeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Builder
    private RecommendRecipeItem(Chat chat, String name, String description) {
        this.chat = chat;
        this.name = name;
        this.description = description;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        chat.getRecommendRecipe().addRecommendRecipeItem(this);
    }

}