package com.recipe.gpt.app.domain.chat;

import com.recipe.gpt.app.domain.chat.chatroom.ChatRoom;
import com.recipe.gpt.app.domain.chat.recommendrecipe.RecommendRecipe;
import com.recipe.gpt.app.domain.chat.recommendrecipe.RecommendRecipeItem;
import com.recipe.gpt.app.domain.chat.requested.ingredient.RequestedIngredient;
import com.recipe.gpt.app.domain.chat.requested.ingredient.RequestedIngredientItem;
import com.recipe.gpt.app.domain.chat.requested.seasoning.RequestedSeasoning;
import com.recipe.gpt.app.domain.chat.requested.seasoning.RequestedSeasoningItem;
import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.domain.recipe.Recipe;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @Embedded
    private RequestedIngredient requestedIngredient = RequestedIngredient.empty();

    @Embedded
    private RequestedSeasoning requestedSeasoning = RequestedSeasoning.empty();

    @Embedded
    private RecommendRecipe recommendRecipe = RecommendRecipe.empty();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public boolean isAccessibleToChat(Member member) {
        if (member == null) {
            return false;
        }

        Long loginMemberId = member.getId();
        Long memberId = this.member.getId();
        return memberId.equals(loginMemberId);
    }

    @Builder
    private Chat(Member member, ChatRoom chatRoom) {
        this.member = member;
        this.chatRoom = chatRoom;
    }

    public Chat update(Chat requestChat,
        List<RequestedIngredientItem> requestedIngredients,
        List<RequestedSeasoningItem> requestedSeasonings,
        List<RecommendRecipeItem> recommendRecipes) {
        updateRequestedIngredients(requestedIngredients);
        updateRequestedSeasonings(requestedSeasonings);
        updateRecommendRecipes(recommendRecipes);
        return this;
    }

    private void updateRequestedIngredients(
        List<RequestedIngredientItem> requestedIngredientItems) {
        this.requestedIngredient.clear();

        for (RequestedIngredientItem requestedIngredientItem : requestedIngredientItems) {
            requestedIngredientItem.setChat(this);
        }
    }

    private void updateRequestedSeasonings(List<RequestedSeasoningItem> requestedSeasoningItems) {
        this.requestedSeasoning.clear();

        for (RequestedSeasoningItem requestedSeasoningItem : requestedSeasoningItems) {
            requestedSeasoningItem.setChat(this);
        }
    }

    private void updateRecommendRecipes(List<RecommendRecipeItem> recommendRecipeItems) {
        this.recommendRecipe.clear();

        for (RecommendRecipeItem recommendRecipeItem : recommendRecipeItems) {
            recommendRecipeItem.setChat(this);
        }
    }

}
