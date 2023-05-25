package com.recipe.gpt.common.config.redis;

import static com.recipe.gpt.common.config.security.jwt.JwtProperty.REFRESH_TOKEN_TIME_TO_LIVE_PRIMITIVE;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RedisHash(value = "refreshToken", timeToLive = REFRESH_TOKEN_TIME_TO_LIVE_PRIMITIVE)
public class RefreshToken {

    @Id
    @Indexed
    private String refreshToken;

    private Long memberId;

}
