package com.recipe.gpt.app.domain.chat;

import com.recipe.gpt.app.domain.chat.chatroom.ChatRoom;
import com.recipe.gpt.app.domain.chat.recommendrecipe.RecommendRecipe;
import com.recipe.gpt.app.domain.chat.requested.ingredient.RequestedIngredient;
import com.recipe.gpt.app.domain.chat.requested.seasoning.RequestedSeasoning;
import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.domain.recipe.Recipe;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipeList = new ArrayList<>();

    @Embedded
    private RequestedIngredient requestedIngredient = RequestedIngredient.empty();

    @Embedded
    private RequestedSeasoning requestedSeasoning = RequestedSeasoning.empty();

    @Embedded
    private RecommendRecipe recommendRecipe = RecommendRecipe.empty();

    @Builder
    private Chat(Member member, ChatRoom chatRoom) {
        this.member = member;
        this.chatRoom = chatRoom;
    }

    public void addRecipe(Recipe recipe) {
        this.recipeList.add(recipe);
    }

}
