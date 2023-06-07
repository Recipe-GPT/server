package com.recipe.gpt.app.domain.chat;

import com.recipe.gpt.app.domain.chat.chatroom.ChatRoom;
import com.recipe.gpt.app.domain.chat.chatroom.ChatRoomService;
import com.recipe.gpt.app.domain.chat.recommendrecipe.RecommendRecipeItem;
import com.recipe.gpt.app.domain.chat.requested.ingredient.RequestedIngredientItem;
import com.recipe.gpt.app.domain.chat.requested.seasoning.RequestedSeasoningItem;
import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.domain.member.MemberService;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecipeRequestDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecommendRequestDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecommendResponseDto;
import com.recipe.gpt.app.web.dto.recipe.ai.ExtractedRecipeResponseDto;
import com.recipe.gpt.app.web.response.ListResponse;
import com.recipe.gpt.common.config.security.context.LoginMember;
import com.recipe.gpt.common.exception.NotPossibleToAccessChatRoomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;

    private final ChatRepository chatRepository;

    private final MemberService memberService;

    private final ChatRoomService chatRoomService;

    /**
     * 요리 추천 질문
     */
    public ListResponse<AiServerRecommendResponseDto> recommendQuery(
        LoginMember loginMember, Long chatRoomId, AiServerRecommendRequestDto body) {
        Member member = memberService.findLoginMember(loginMember);
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);

        if (!chatRoom.isAccessibleToChatRoom(member)) {
            throw new NotPossibleToAccessChatRoomException();
        }

        List<AiServerRecommendResponseDto> responseList = chatClient.recommendQuery(body);
        save(member, chatRoom, body, responseList);
        return ListResponse.create(responseList);
    }

    /**
     * 레시피 질문
     */
    public ExtractedRecipeResponseDto recipeQuery(
        LoginMember loginMember, Long chatRoomId, AiServerRecipeRequestDto body) {
        ExtractedRecipeResponseDto response = chatClient.recipeQuery(body);
        return response;
    }

    @Transactional
    public void save(Member member,
        ChatRoom chatRoom,
        AiServerRecommendRequestDto body,
        List<AiServerRecommendResponseDto> responseList) {

        Chat chat = Chat.builder()
            .chatRoom(chatRoom)
            .member(member)
            .build();

        List<RequestedIngredientItem> requestedIngredientItems = body.toRequestedIngredientItems();
        List<RequestedSeasoningItem> requestedSeasoningItems = body.toRequestedSeasoningItems();
        List<RecommendRecipeItem> recommendRecipeItems = responseList.stream()
            .map(AiServerRecommendResponseDto::toRecommendRecipeItem)
            .toList();

        setChat(chat, requestedIngredientItems, requestedSeasoningItems, recommendRecipeItems);
        chatRepository.save(chat);
    }

    private void setChat(Chat chat,
        List<RequestedIngredientItem> requestedIngredientItems,
        List<RequestedSeasoningItem> requestedSeasoningItems,
        List<RecommendRecipeItem> recommendRecipeItems) {
        for (RequestedIngredientItem requestedIngredientItem : requestedIngredientItems) {
            requestedIngredientItem.setChat(chat);
        }
        for (RequestedSeasoningItem requestedSeasoningItem : requestedSeasoningItems) {
            requestedSeasoningItem.setChat(chat);
        }
        for (RecommendRecipeItem recommendRecipeItem : recommendRecipeItems) {
            recommendRecipeItem.setChat(chat);
        }
    }

}
