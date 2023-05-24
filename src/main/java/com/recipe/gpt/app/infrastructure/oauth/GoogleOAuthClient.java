package com.recipe.gpt.app.infrastructure.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.recipe.gpt.app.domain.auth.OAuthClient;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.recipe.gpt.app.infrastructure.oauth.dto.GoogleTokenResponseDto;
import com.recipe.gpt.app.infrastructure.oauth.dto.UserInfo;
import com.recipe.gpt.app.web.dto.auth.OAuthMember;
import com.recipe.gpt.common.config.properties.GoogleProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleOAuthClient implements OAuthClient {

    private static final String JWT_DELIMITER = "\\.";

    private final GoogleProperties properties;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public GoogleOAuthClient(GoogleProperties properties, RestTemplateBuilder restTemplateBuilder,
        final ObjectMapper objectMapper) {
        this.properties = properties;
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public OAuthMember getOAuthMember(String code) {
        GoogleTokenResponseDto googleTokenResponse = requestGoogleToken(code);
        String payload = getPayload(googleTokenResponse.getIdToken());
        UserInfo userInfo = parseUserInfo(payload);

        String refreshToken = googleTokenResponse.getRefreshToken();
        return new OAuthMember(userInfo.getEmail(), userInfo.getName(), userInfo.getPicture(), refreshToken);
    }

    private GoogleTokenResponseDto requestGoogleToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = generateTokenParams(code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        return fetchGoogleToken(request).getBody();
    }

    private ResponseEntity<GoogleTokenResponseDto> fetchGoogleToken(
        final HttpEntity<MultiValueMap<String, String>> request) {
        try {
            return restTemplate.postForEntity("https://oauth2.googleapis.com/token", request, GoogleTokenResponseDto.class);
        } catch (final RestClientException e) {
            // TODO :: 수정
//            throw new OAuthException(e);
            throw new RuntimeException();
        }
    }

    private MultiValueMap<String, String> generateTokenParams(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", properties.getClientId());
        params.add("client_secret", properties.getClientSecret());
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", properties.getRedirectUri());
        return params;
    }

    private String getPayload(String jwt) {
        return jwt.split(JWT_DELIMITER)[1];
    }

    private UserInfo parseUserInfo(String payload) {
        String decodedPayload = decodeJwtPayload(payload);
        try {
            return objectMapper.readValue(decodedPayload, UserInfo.class);
        } catch (final JsonProcessingException e) {
            // TODO :: 수정
//            throw new OAuthException("id 토큰을 읽을 수 없습니다.", e);
            throw new RuntimeException();
        }
    }

    private String decodeJwtPayload(String payload) {
        return new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
    }

}
