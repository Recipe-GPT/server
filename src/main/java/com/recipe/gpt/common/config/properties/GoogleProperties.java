package com.recipe.gpt.common.config.properties;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@ConfigurationProperties("oauth.google")
public class GoogleProperties {

    private final String clientId;
    private final String clientSecret;
    private final String oAuthEndPoint;
    private final String responseType;
    private final List<String> scopes;
    private final String tokenUri;
    private final String accessType;
    private final String redirectUri;


}
