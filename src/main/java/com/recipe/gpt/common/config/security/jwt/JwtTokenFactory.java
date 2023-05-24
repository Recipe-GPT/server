package com.recipe.gpt.common.config.security.jwt;

import static com.recipe.gpt.common.config.security.jwt.JwtProperty.JWT_ISSUER;
import static com.recipe.gpt.common.config.security.jwt.JwtProperty.MEMBER_EMAIL;
import static com.recipe.gpt.common.config.security.jwt.JwtProperty.REGISTRATION_ID;
import static com.recipe.gpt.common.config.security.jwt.JwtProperty.SIGN_KEY;

import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.web.dto.auth.AccessAndRefreshTokenResponseDto;
import com.recipe.gpt.app.web.dto.auth.AccessTokenResponseDto;
import com.recipe.gpt.app.web.dto.auth.RefreshTokenResponseDto;
import com.recipe.gpt.common.config.redis.RefreshToken;
import com.recipe.gpt.common.config.redis.RefreshTokenRepository;
import com.recipe.gpt.common.exception.NotFoundRefreshTokenException;
import com.recipe.gpt.common.util.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenFactory {

    private final RefreshTokenRepository refreshTokenRepository;

    public AccessAndRefreshTokenResponseDto generateJwtToken(Member member) {
        AccessTokenResponseDto accessToken = generateAccessToken(member);
        RefreshTokenResponseDto refreshToken = generateRefreshToken(member);

        return AccessAndRefreshTokenResponseDto.of(
            accessToken,
            refreshToken
        );
    }

    /**
     * 엑세스 토큰 발급
     */
    public AccessTokenResponseDto generateAccessToken(Member member) {
        Date now = DateUtils.now();
        // TODO::환경변수
        Date expiredDate = DateUtils.addTime(now, 1800000L);
        LocalDateTime expiredLocalDateTime = LocalDateTime.ofInstant(expiredDate.toInstant(),
            ZoneId.systemDefault());

        String accessToken = Jwts.builder()
            .setClaims(createJwtClaims(member))
            .setIssuedAt(now)
            .setIssuer(JWT_ISSUER)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS256, SIGN_KEY)
            .compact();

        return AccessTokenResponseDto.of(
            accessToken,
            expiredLocalDateTime.toString()
        );
    }

    private Map<String, Object> createJwtClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(MEMBER_EMAIL, member.getEmail());
        // TODO::변수명 수정
        claims.put(REGISTRATION_ID, member.getId());
        return claims;
    }

    public RefreshTokenResponseDto generateRefreshToken(Member member) {
        Date now = DateUtils.now();
        // TODO::환경변수
        Date expiredDate = DateUtils.addTime(now, 604800000L);
        LocalDateTime expiredLocalDateTime = LocalDateTime.ofInstant(expiredDate.toInstant(),
            ZoneId.systemDefault());

        String refreshToken = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, SIGN_KEY)
            .setIssuedAt(now)
            .setIssuer(JWT_ISSUER)
            .setExpiration(expiredDate)
            .compact();

        // Redis 저장
        RefreshToken redisRefreshToken = new RefreshToken(refreshToken, member.getId());
        refreshTokenRepository.save(redisRefreshToken);

        return RefreshTokenResponseDto.of(
            refreshToken,
            expiredLocalDateTime.toString()
        );
    }

    public Claims parseClaims(String token) {
        return Jwts
            .parser()
            .setSigningKey(JwtProperty.SIGN_KEY)
            .parseClaimsJws(token)
            .getBody();
    }

    public RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
            .orElseThrow(NotFoundRefreshTokenException::new);
    }

}
