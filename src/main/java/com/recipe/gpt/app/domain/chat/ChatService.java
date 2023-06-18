package com.recipe.gpt.app.domain.chat;

import com.recipe.gpt.app.domain.chat.chatroom.ChatRoom;
import com.recipe.gpt.app.domain.chat.chatroom.ChatRoomService;
import com.recipe.gpt.app.domain.chat.requested.ingredient.RequestedIngredientItem;
import com.recipe.gpt.app.domain.chat.requested.seasoning.RequestedSeasoningItem;
import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.domain.member.MemberService;
import com.recipe.gpt.app.domain.recipe.Recipe;
import com.recipe.gpt.app.domain.recipe.RecipeService;
import com.recipe.gpt.app.web.dto.ai.AiServerRecipeRequestDto;
import com.recipe.gpt.app.web.dto.ai.AiServerRecommendRequestDto;
import com.recipe.gpt.app.web.dto.ai.AiServerRecommendResponseDto;
import com.recipe.gpt.app.web.dto.ai.ExtractedRecipeResponseDto;
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

    private final RecipeService recipeService;

    /**
     * 요리 추천 질문
     */
    public ListResponse<AiServerRecommendResponseDto> recommendQuery(
        LoginMember loginMember, Long chatRoomId, AiServerRecommendRequestDto body) {
        Member member = memberService.findLoginMember(loginMember);
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);

        // [1] 채팅방 접근 권한 체크
        if (!chatRoom.isAccessibleToChatRoom(member)) {
            throw new NotPossibleToAccessChatRoomException();
        }

        // [2] ai 서버로 요청
        List<AiServerRecommendResponseDto> responseList = chatClient.recommendQuery(body);

        // [3] 채팅 저장
        Chat chat = save(member, chatRoom, body);

        // [4] 응답 레시피 모두 저장
        for (AiServerRecommendResponseDto response : responseList) {
            Recipe recipe = recipeService.createByRecommendQueryResponse(response, chat);
            response.setRecipeId(recipe.getId());
        }

        return ListResponse.of(responseList);
    }

    /**
     * 레시피 질문
     */
    @Transactional
    public ExtractedRecipeResponseDto recipeQuery(
        LoginMember loginMember, Long chatRoomId, Long recipeId) {
        Member member = memberService.findLoginMember(loginMember);
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);
        Recipe recipe = recipeService.findById(recipeId);

        // [1] 채팅방 접근 권한 체크
        if (!chatRoom.isAccessibleToChatRoom(member)) {
            throw new NotPossibleToAccessChatRoomException();
        }

        // [2] AI 서버로 요청
        AiServerRecipeRequestDto body = AiServerRecipeRequestDto.of(recipe);
        ExtractedRecipeResponseDto response = chatClient.recipeQuery(body);

        // [3] 레시피 업데이트
        recipeService.updateRecipe(recipe, response);
        return response;
    }

    @Transactional
    public Chat save(Member member,
        ChatRoom chatRoom,
        AiServerRecommendRequestDto body) {

        Chat chat = Chat.builder()
            .chatRoom(chatRoom)
            .member(member)
            .build();

        List<RequestedIngredientItem> requestedIngredientItems = body.toRequestedIngredientItems();
        List<RequestedSeasoningItem> requestedSeasoningItems = body.toRequestedSeasoningItems();

        setChat(chat, requestedIngredientItems, requestedSeasoningItems);
        return chatRepository.save(chat);
    }

    private void setChat(Chat chat,
        List<RequestedIngredientItem> requestedIngredientItems,
        List<RequestedSeasoningItem> requestedSeasoningItems) {
        for (RequestedIngredientItem requestedIngredientItem : requestedIngredientItems) {
            requestedIngredientItem.setChat(chat);
        }
        for (RequestedSeasoningItem requestedSeasoningItem : requestedSeasoningItems) {
            requestedSeasoningItem.setChat(chat);
        }
    }

}
