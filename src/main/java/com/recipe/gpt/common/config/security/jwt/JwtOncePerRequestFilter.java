package com.recipe.gpt.common.config.security.jwt;

import static com.recipe.gpt.common.config.security.jwt.JwtProperty.JWT_EXCEPTION;
import static com.recipe.gpt.common.config.security.jwt.JwtProperty.TOKEN_KEY;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtOncePerRequestFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(TOKEN_KEY);

        try {
            authenticate(token);
        } catch (ExpiredJwtException e) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.EXPIRED);
        } catch (UnsupportedJwtException e) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.UNSUPPORTED);
        } catch (MalformedJwtException e) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.MALFORMED);
        } catch (SignatureException e) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.INVALID_SIGNATURE);
        } catch (IllegalArgumentException e) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.INVALID);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticate(String token) {
        if (Strings.isNotBlank(token)) {
            Authentication auth = jwtAuthenticationProvider.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }

}
