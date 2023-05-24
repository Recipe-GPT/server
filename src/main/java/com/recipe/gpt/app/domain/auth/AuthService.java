package com.recipe.gpt.app.domain.auth;

import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.domain.member.MemberRepository;
import com.recipe.gpt.app.web.dto.auth.AccessAndRefreshTokenResponseDto;
import com.recipe.gpt.app.web.dto.auth.AccessTokenResponseDto;
import com.recipe.gpt.app.web.dto.auth.OAuthMember;
import com.recipe.gpt.app.web.dto.auth.TokenRenewalRequestDto;
import com.recipe.gpt.common.config.redis.RefreshToken;
import com.recipe.gpt.common.config.security.jwt.JwtTokenFactory;
import com.recipe.gpt.common.exception.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenFactory jwtTokenFactory;

    @Transactional
    public AccessAndRefreshTokenResponseDto generateAccessAndRefreshToken(OAuthMember oAuthMember) {
        Member foundMember = findMember(oAuthMember);
        return jwtTokenFactory.generateJwtToken(foundMember);
    }

    private Member findMember(final OAuthMember oAuthMember) {
        String email = oAuthMember.getEmail();
        if (memberRepository.existsByEmail(email)) {
            return memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);
        }
        return memberRepository.save(oAuthMember.toMember());
    }

    public AccessTokenResponseDto refreshAccessToken(TokenRenewalRequestDto body) {
        RefreshToken refreshToken = jwtTokenFactory.getRefreshToken(body.getRefreshToken());
        Member member = memberRepository.findById(refreshToken.getMemberId())
            .orElseThrow(NotFoundMemberException::new);
        return jwtTokenFactory.generateAccessToken(member);
    }

}
