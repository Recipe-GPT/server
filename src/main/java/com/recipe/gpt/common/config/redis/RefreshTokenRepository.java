package com.recipe.gpt.common.config.redis;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    Optional<RefreshToken> findByMemberId(Long memberId);

}