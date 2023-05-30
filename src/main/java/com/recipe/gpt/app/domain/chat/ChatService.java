package com.recipe.gpt.app.domain.chat;

import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecipeRequestDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecipeResponseDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecommendRequestDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRecommendResponseDto;
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
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("ingredients", body.getIngredients());
        bodyMap.put("seasonings", body.getSeasonings());

        WebClient webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .defaultHeader("x-api-key", apiKey)
            .build();

        AiServerRecommendResponseDto[] responseArray = webClient
            .post()
            .uri(recommendUri)
            .bodyValue(bodyMap)
            .retrieve()
            .bodyToMono(AiServerRecommendResponseDto[].class)
            .block();

        List<AiServerRecommendResponseDto> responseList = Arrays.asList(responseArray);
        return ListResponse.create(responseList);
    }

    public AiServerRecipeResponseDto recipeQuery(AiServerRecipeRequestDto body) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("name", body.getName());
        bodyMap.put("description", body.getDescription());
        bodyMap.put("ingredients", body.getIngredients());
        bodyMap.put("seasonings", body.getSeasonings());

        WebClient webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .defaultHeader("x-api-key", apiKey)
            .build();

        AiServerRecipeResponseDto response = webClient
            .post()
            .uri(recipeUri)
            .bodyValue(bodyMap)
            .retrieve()
            .bodyToMono(AiServerRecipeResponseDto.class)
            .block();

        return response;
    }

}
