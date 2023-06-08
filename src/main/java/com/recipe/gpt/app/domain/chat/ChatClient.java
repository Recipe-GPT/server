package com.recipe.gpt.app.domain.chat;

import com.recipe.gpt.app.web.dto.ai.AiServerRecipeRequestDto;
import com.recipe.gpt.app.web.dto.ai.AiServerRecipeResponseDto;
import com.recipe.gpt.app.web.dto.ai.AiServerRecommendRequestDto;
import com.recipe.gpt.app.web.dto.ai.AiServerRecommendResponseDto;
import com.recipe.gpt.app.web.dto.ai.ExtractedRecipeResponseDto;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ChatClient {

    @Value("${api.x-api-key}")
    private String apiKey;

    @Value("${api.base-url}")
    private String baseUrl;

    @Value("${api.uri-recommend}")
    private String recommendUri;

    @Value("${api.uri-recipe}")
    private String recipeUri;

    /**
     * 요리 추천 질문
     */
    public List<AiServerRecommendResponseDto> recommendQuery(AiServerRecommendRequestDto body) {
        // [1] DTO 설정
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("ingredients", body.getIngredients());
        bodyMap.put("seasonings", body.getSeasonings());

        // [2] BaseWebClient
        WebClient baseWebClient = getBaseWebClient();

        // [3] 외부 API 서버에 Post 요청
        AiServerRecommendResponseDto[] responseArray = postRequest(
            recommendUri,
            bodyMap,
            AiServerRecommendResponseDto[].class,
            baseWebClient);

        return Arrays.asList(responseArray);
    }

    /**
     * 레시피 질문
     */
    public ExtractedRecipeResponseDto recipeQuery(AiServerRecipeRequestDto body) {
        // [1] DTO 설정
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("name", body.getName());
        bodyMap.put("description", body.getDescription());
        bodyMap.put("ingredients", body.getIngredients());
        bodyMap.put("seasonings", body.getSeasonings());

        // [2] BaseWebClient
        WebClient baseWebClient = getBaseWebClient();

        // [3] 외부 API 서버에 Post 요청
        AiServerRecipeResponseDto response = postRequest(
            recipeUri,
            bodyMap,
            AiServerRecipeResponseDto.class,
            baseWebClient);

        return ExtractedRecipeResponseDto.of(response);
    }

    public WebClient getBaseWebClient() {
        return WebClient
            .builder()
            .baseUrl(baseUrl)
            .defaultHeader("x-api-key", apiKey)
            .build();
    }

    private <T> T postRequest(String uri, Map<String, Object> bodyMap, Class<T> responseType,
        WebClient webClient) {
        return webClient
            .post()
            .uri(uri)
            .bodyValue(bodyMap)
            .retrieve()
            .bodyToMono(responseType)
            .block();
    }

}
