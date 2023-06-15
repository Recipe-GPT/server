package com.recipe.gpt.common.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("spring.security.oauth2.client.registration.google")
public class GoogleProperties {

    private final String clientId;

    private final String clientSecret;

    private final String redirectUri;

}
