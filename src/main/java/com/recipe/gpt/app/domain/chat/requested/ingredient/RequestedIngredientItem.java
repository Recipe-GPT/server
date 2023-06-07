package com.recipe.gpt.app.domain.chat.requested.ingredient;

import com.recipe.gpt.app.domain.chat.Chat;
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
public class RequestedIngredientItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Builder
    private RequestedIngredientItem(Chat chat, String name) {
        this.chat = chat;
        this.name = name;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        chat.getRequestedIngredient().addRequestedIngredientItem(this);
    }

    public boolean isSameIngredientWith(RequestedIngredientItem requestedIngredientItem) {
        return this.name.equals(requestedIngredientItem.name);
    }

}
