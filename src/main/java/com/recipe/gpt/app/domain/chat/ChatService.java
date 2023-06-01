package com.recipe.gpt.app.domain.chat;

import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecipeRequestDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecipeResponseDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecommendRequestDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecommendResponseDto;
import com.recipe.gpt.app.web.dto.recipe.ai.ExtractedRecipeResponseDto;
import com.recipe.gpt.app.web.response.ListResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ChatService {

    @Value("${api.x-api-key}")
    private String apiKey;

    @Value("${api.base-url}")
    private String baseUrl;

    @Value("${api.uri-recommend}")
    private String recommendUri;

    @Value("${api.uri-recipe}")
    private String recipeUri;

    /**
     * 레시피 질문
     */
    public ListResponse<AiServerRecommendResponseDto> recommendQuery(AiServerRecommendRequestDto body) {
        // [1] DTO 설정
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("ingredients", body.getIngredients());
        bodyMap.put("seasonings", body.getSeasonings());

        // [2] BaseWebClient
        WebClient baseWebClient = getBaseWebClient();

        // [3] 외부 API 서버에 Post 요청
        AiServerRecommendResponseDto[] responseArray = postRequest(recommendUri, bodyMap, AiServerRecommendResponseDto[].class, baseWebClient);
        List<AiServerRecommendResponseDto> responseList = Arrays.asList(responseArray);

        return ListResponse.create(responseList);
    }

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
        AiServerRecipeResponseDto response = postRequest(recipeUri, bodyMap, AiServerRecipeResponseDto.class, baseWebClient);

        return ExtractedRecipeResponseDto.of(response);
    }

    public WebClient getBaseWebClient() {
        return WebClient
            .builder()
            .baseUrl(baseUrl)
            .defaultHeader("x-api-key", apiKey)
            .build();
    }

    private <T> T postRequest(String uri, Map<String, Object> bodyMap, Class<T> responseType, WebClient webClient) {
        return webClient
            .post()
            .uri(uri)
            .bodyValue(bodyMap)
            .retrieve()
            .bodyToMono(responseType)
            .block();
    }

}
