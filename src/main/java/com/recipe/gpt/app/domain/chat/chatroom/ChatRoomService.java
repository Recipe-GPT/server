package com.recipe.gpt.app.domain.chat.chatroom;

import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.domain.member.MemberService;
import com.recipe.gpt.app.web.dto.chat.ChatResponseDto;
import com.recipe.gpt.app.web.dto.chat.chatroom.ChatRoomRequestDto;
import com.recipe.gpt.app.web.dto.chat.chatroom.ChatRoomResponseDto;
import com.recipe.gpt.app.web.response.ListResponse;
import com.recipe.gpt.common.config.security.context.LoginMember;
import com.recipe.gpt.common.exception.ChatRoomNotFoundException;
import com.recipe.gpt.common.exception.NotPossibleToAccessChatRoomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final MemberService memberService;

    private final ChatRoomRepository chatRoomRepository;

    /**
     * 채팅방 생성
     */
    @Transactional
    public Long create(LoginMember loginMember, ChatRoomRequestDto body) {
        Member member = memberService.findLoginMember(loginMember);

        ChatRoom chatRoom = chatRoomRepository.save(body.toChatRoom(member));
        return chatRoom.getId();
    }

    /**
     * 채팅방 삭제
     */
    @Transactional
    public void deleteChatRoom(LoginMember loginMember, Long id) {
        Member member = memberService.findLoginMember(loginMember);

        ChatRoom chatRoom = findById(id);

        if (!chatRoom.isAccessibleToChatRoom(member)) {
            throw new NotPossibleToAccessChatRoomException();
        }

        chatRoomRepository.delete(chatRoom);
    }

    /**
     * 내 채팅방 조회
     */
    @Transactional(readOnly = true)
    public ListResponse<ChatRoomResponseDto> findMyChatRoom(LoginMember loginMember) {
        Member member = memberService.findLoginMember(loginMember);

        List<ChatRoom> myChatRoomList = chatRoomRepository.findChatRoomsByMember(member);
        List<ChatRoomResponseDto> chatRoomResponseDtoList = ChatRoomResponseDto.listOf(
            myChatRoomList);
        return ListResponse.create(chatRoomResponseDtoList);
    }

    /**
     * 채팅방 수정
     */
    @Transactional
    public void updateChatRoom(LoginMember loginMember, Long id, ChatRoomRequestDto body) {
        Member member = memberService.findLoginMember(loginMember);
        ChatRoom chatRoom = findById(id);

        if (!chatRoom.isAccessibleToChatRoom(member)) {
            throw new NotPossibleToAccessChatRoomException();
        }

        ChatRoom requestChatRoom = body.toChatRoom(member);
        chatRoom.update(requestChatRoom);
    }

    /**
     * 채팅 조회
     */
    public ListResponse<ChatResponseDto> findChatList(LoginMember loginMember, Long chatRoomId) {
        Member member = memberService.findLoginMember(loginMember);
        ChatRoom chatRoom = findById(chatRoomId);

        if (!chatRoom.isAccessibleToChatRoom(member)) {
            throw new NotPossibleToAccessChatRoomException();
        }

        List<ChatResponseDto> chatResponseDtoList = ChatResponseDto.listOf(chatRoom.getChatList());

        return ListResponse.create(chatResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ChatRoom findById(Long id) {
        return chatRoomRepository.findById(id)
            .orElseThrow(ChatRoomNotFoundException::new);
    }

}
