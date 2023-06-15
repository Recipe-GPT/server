package com.recipe.gpt.app.domain.auth;

import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.domain.member.MemberRepository;
import com.recipe.gpt.app.web.dto.auth.AccessAndRefreshTokenResponseDto;
import com.recipe.gpt.app.web.dto.auth.AccessTokenResponseDto;
import com.recipe.gpt.app.web.dto.auth.OAuthMember;
import com.recipe.gpt.app.web.dto.auth.RefreshTokenRequestDto;
import com.recipe.gpt.common.config.redis.RefreshToken;
import com.recipe.gpt.common.config.security.jwt.JwtTokenFactory;
import com.recipe.gpt.common.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    private final JwtTokenFactory jwtTokenFactory;

    /**
     * 액세스 토큰, 리프레시 토큰 생성
     */
    @Transactional(readOnly = true)
    public AccessAndRefreshTokenResponseDto generateAccessAndRefreshToken(OAuthMember oAuthMember) {
        Member foundMember = findMember(oAuthMember);
        return jwtTokenFactory.generateJwtToken(foundMember);
    }

    /**
     * 리프레시 토큰으로 액세스 토큰 생성
     */
    @Transactional(readOnly = true)
    public AccessTokenResponseDto refreshAccessToken(RefreshTokenRequestDto body) {
        RefreshToken refreshToken = jwtTokenFactory.findRefreshToken(body.getRefreshToken());
        Member member = memberRepository.findById(refreshToken.getMemberId())
            .orElseThrow(MemberNotFoundException::new);
        return jwtTokenFactory.generateAccessToken(member);
    }

    private Member findMember(final OAuthMember oAuthMember) {
        String email = oAuthMember.getEmail();
        if (memberRepository.existsByEmail(email)) {
            return memberRepository.getByEmail(email);
        }
        return memberRepository.save(oAuthMember.toMember());
    }

    /**
     * 리프레시 토큰 삭제
     */
    public void expirationRefreshToken(RefreshTokenRequestDto body) {
        jwtTokenFactory.expirationRefreshToken(body.getRefreshToken());
    }

}
