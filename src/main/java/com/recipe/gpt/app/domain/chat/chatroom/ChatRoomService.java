package com.recipe.gpt.app.domain.chat.chatroom;

import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.domain.member.MemberService;
import com.recipe.gpt.app.web.dto.chat.chatroom.ChatRoomRequestDto;
import com.recipe.gpt.common.config.security.context.LoginMember;
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

}
