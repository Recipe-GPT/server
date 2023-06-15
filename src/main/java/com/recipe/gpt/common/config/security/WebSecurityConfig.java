package com.recipe.gpt.common.config.security;

import com.recipe.gpt.app.web.path.ApiPath;
import com.recipe.gpt.common.config.security.jwt.CustomAccessDeniedHandler;
import com.recipe.gpt.common.config.security.jwt.JwtOncePerRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtOncePerRequestFilter jwtOncePerRequestFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private static final String[] WHITE_LIST = {
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/resources/**",
        "/"
    };

    @Value("${recipe-gpt.error.redirectUrl}")
    private String errorAuth;

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.formLogin().disable();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.cors();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.oauth2Login()
            .loginPage("/login")
            .defaultSuccessUrl("/home")
            .failureUrl("/login?error");

        http.authorizeHttpRequests(request -> request
            .requestMatchers(WHITE_LIST).permitAll()
            // 인증
            .requestMatchers(ApiPath.LOGIN_OAUTH2, ApiPath.REFRESH_TOKEN).permitAll()
            // 에러 핸들러
            .requestMatchers(errorAuth).permitAll()
            // 식재료, 양념 검색
            .requestMatchers(ApiPath.SEARCH_INGREDIENT, ApiPath.SEARCH_SEASONING).permitAll()
            // 커뮤니티 조회
            .requestMatchers(
                ApiPath.BOARD_VIEW_RECOMMEND,
                ApiPath.BOARD_VIEW_TRENDING,
                ApiPath.BOARD_VIEW_FILTER,
                ApiPath.BOARD_VIEW_DETAIL).permitAll()
            .anyRequest().authenticated()
        );
        http.addFilterBefore(jwtOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(customAuthenticationEntryPoint)
            .accessDeniedHandler(customAccessDeniedHandler);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}