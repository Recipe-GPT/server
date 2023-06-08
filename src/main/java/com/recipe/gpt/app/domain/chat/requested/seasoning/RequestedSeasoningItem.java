package com.recipe.gpt.app.domain.chat.requested.seasoning;

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
public class RequestedSeasoningItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Builder
    private RequestedSeasoningItem(Chat chat, String name) {
        this.chat = chat;
        this.name = name;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        chat.getRequestedSeasoning().addRequestedSeasoningItem(this);
    }

    public boolean isSameSeasoningWith(RequestedSeasoningItem requestedSeasoningItem) {
        return this.name.equals(requestedSeasoningItem.name);
    }

}