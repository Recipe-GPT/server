package com.recipe.gpt.common.config.redis;

import org.springframework.beans.factory.annotation.Value;

@Value("\${spring.security.jwt.refresh-token-validity-in-seconds}")
private Long REFRESH_TOKEN_TIME_TO_LIVE = -1L;

@RedisHash(value = "refreshToken", timeToLive = REFRESH_TOKEN_TIME_TO_LIVE)
class RefreshToken (

    @Id
    @Indexed
    val refreshToken: String,
    val memberId: Long,

    )
