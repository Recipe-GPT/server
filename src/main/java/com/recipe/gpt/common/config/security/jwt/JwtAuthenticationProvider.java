package com.recipe.gpt.common.config.security.jwt;

import static com.recipe.gpt.common.config.security.jwt.JwtProperty.MEMBER_EMAIL;
import static com.recipe.gpt.common.config.security.jwt.JwtProperty.ID;

import com.recipe.gpt.common.config.security.context.LoginMember;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider {

    private final JwtTokenFactory jwtTokenFactory;

    public Authentication authenticate(String token) throws AuthenticationException {
        Claims claims = jwtTokenFactory.parseClaims(token);

        Long id = claims.get(ID, Long.class);
        String email = claims.get(MEMBER_EMAIL, String.class);
        LoginMember loginMember = LoginMember.of(id, email);

        return new UsernamePasswordAuthenticationToken(loginMember, null, null);
    }

}
