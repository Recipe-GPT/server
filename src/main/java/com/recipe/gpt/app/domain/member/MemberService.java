package com.recipe.gpt.app.domain.member;

import com.recipe.gpt.app.web.dto.member.MemberResponseDto;
import com.recipe.gpt.common.config.security.context.LoginMember;
import com.recipe.gpt.common.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 내 정보 불러오기
     */
    @Transactional(readOnly = true)
    public MemberResponseDto findMemberSelf(LoginMember loginMember) {
        Long id = loginMember.getId();

        Member member = memberRepository.findById(id)
            .orElseThrow(MemberNotFoundException::new);

        return MemberResponseDto.of(member);
    }

    @Transactional(readOnly = true)
    public Member findLoginMember(LoginMember loginMember) {
        if (loginMember == null) {
            return null;
        }

        Long loginMemberId = loginMember.getId();
        return memberRepository.getReferenceById(loginMemberId);
    }

}
