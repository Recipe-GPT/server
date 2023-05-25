package com.recipe.gpt.app.domain.member;

import com.recipe.gpt.app.web.dto.member.FindMemberSelfResponseDto;
import com.recipe.gpt.common.config.security.context.LoginMember;
import com.recipe.gpt.common.exception.NotFoundMemberException;
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
    public FindMemberSelfResponseDto findMemberSelf(LoginMember loginMember) {
        Long id = loginMember.getId();

        Member member = memberRepository.findById(id)
            .orElseThrow(NotFoundMemberException::new);

        return FindMemberSelfResponseDto.of(member);
    }

}
