package com.recipe.gpt.common.config.security.jwt;

import static com.recipe.gpt.common.config.security.jwt.JwtConstants.ID;
import static com.recipe.gpt.common.config.security.jwt.JwtConstants.JWT_ISSUER;
import static com.recipe.gpt.common.config.security.jwt.JwtConstants.MEMBER_EMAIL;

import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.web.dto.auth.AccessAndRefreshTokenResponseDto;
import com.recipe.gpt.app.web.dto.auth.AccessTokenResponseDto;
import com.recipe.gpt.app.web.dto.auth.RefreshTokenResponseDto;
import com.recipe.gpt.common.config.properties.JwtProperties;
import com.recipe.gpt.common.config.redis.RefreshToken;
import com.recipe.gpt.common.config.redis.RefreshTokenRepository;
import com.recipe.gpt.common.exception.RefreshTokenNotFoundException;
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

    private final JwtProperties properties;

    /**
     * 액세스, 리프레시 토큰 발급
     */
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
        Date expiredDate = DateUtils.addTime(now, properties.getAccessTokenValidityInSeconds());
        LocalDateTime expiredLocalDateTime = LocalDateTime.ofInstant(expiredDate.toInstant(),
            ZoneId.systemDefault());

        String accessToken = Jwts.builder()
            .setClaims(createJwtClaims(member))
            .setIssuedAt(now)
            .setIssuer(JWT_ISSUER)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS256, properties.getSignKey())
            .compact();

        return AccessTokenResponseDto.of(
            accessToken,
            expiredLocalDateTime.toString()
        );
    }

    /**
     * 리프레시 토큰 발급
     */
    public RefreshTokenResponseDto generateRefreshToken(Member member) {
        Date now = DateUtils.now();
        Date expiredDate = DateUtils.addTime(now, properties.getRefreshTokenValidityInSeconds());
        LocalDateTime expiredLocalDateTime = LocalDateTime.ofInstant(expiredDate.toInstant(),
            ZoneId.systemDefault());

        String refreshToken = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, properties.getSignKey())
            .setIssuedAt(now)
            .setIssuer(JWT_ISSUER)
            .setExpiration(expiredDate)
            .compact();

        // Redis 저장
        RefreshToken redisRefreshToken = new RefreshToken(
            properties.getRefreshTokenValidityInSeconds(),
            refreshToken,
            member.getId());
        refreshTokenRepository.save(redisRefreshToken);

        return RefreshTokenResponseDto.of(
            refreshToken,
            expiredLocalDateTime.toString()
        );
    }

    /**
     * 리프레시 토큰 삭제
     */
    public void expirationRefreshToken(String refreshToken) {
        RefreshToken redisRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
            .orElseThrow(RefreshTokenNotFoundException::new);

        // Redis 삭제
        refreshTokenRepository.delete(redisRefreshToken);
    }

    private Map<String, Object> createJwtClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(MEMBER_EMAIL, member.getEmail());
        claims.put(ID, member.getId());
        return claims;
    }

    public Claims parseClaims(String token) {
        return Jwts
            .parser()
            .setSigningKey(properties.getSignKey())
            .parseClaimsJws(token)
            .getBody();
    }

    public RefreshToken findRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
            .orElseThrow(RefreshTokenNotFoundException::new);
    }

}
