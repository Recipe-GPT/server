package com.recipe.gpt.app.domain.chat.chatroom;

import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.domain.member.MemberService;
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

    @Transactional
    public Long create(LoginMember loginMember, ChatRoomRequestDto body) {
        Member member = memberService.findLoginMember(loginMember);

        ChatRoom chatRoom = chatRoomRepository.save(body.toChatRoom(member));
        return chatRoom.getId();
    }

    @Transactional
    public void deleteChatRoom(LoginMember loginMember, Long id) {
        Member member = memberService.findLoginMember(loginMember);

        ChatRoom chatRoom = findById(id);

        if (!chatRoom.isAccessibleChatRoom(member)) {
            throw new NotPossibleToAccessChatRoomException();
        }

        chatRoomRepository.delete(chatRoom);
    }

    @Transactional(readOnly = true)
    public ListResponse<ChatRoomResponseDto> findMyChatRoom(LoginMember loginMember) {
        Member member = memberService.findLoginMember(loginMember);

        List<ChatRoom> myChatRoomList = chatRoomRepository.findChatRoomsByMember(member);
        List<ChatRoomResponseDto> chatRoomResponseDtoList = ChatRoomResponseDto.listOf(
            myChatRoomList);
        return ListResponse.create(chatRoomResponseDtoList);
    }

    @Transactional
    public void updateChatRoom(LoginMember loginMember, Long id, ChatRoomRequestDto body) {
        Member member = memberService.findLoginMember(loginMember);
        ChatRoom chatRoom = findById(id);

        if (!chatRoom.isAccessibleChatRoom(member)) {
            throw new NotPossibleToAccessChatRoomException();
        }

        ChatRoom requestChatRoom = body.toChatRoom(member);
        chatRoom.update(requestChatRoom);
    }

    private ChatRoom findById(Long id) {
        return chatRoomRepository.findById(id)
            .orElseThrow(ChatRoomNotFoundException::new);
    }

}
