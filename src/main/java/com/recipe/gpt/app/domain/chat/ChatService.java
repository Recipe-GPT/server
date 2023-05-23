package com.recipe.gpt.app.domain.chat;


import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRequestDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerResponseDto;
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

    @Value("${api.uri}")
    private String uri;

    /**
     * 레시피 질문
     */
    public ListResponse<AiServerResponseDto> recipeQuery(AiServerRequestDto body) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("ingredients", body.getIngredients());
        bodyMap.put("seasonings", body.getSeasonings());

        WebClient webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .defaultHeader("x-api-key", apiKey)
            .build();

        AiServerResponseDto[] responseArray = webClient
            .post()
            .uri(uri)
            .bodyValue(bodyMap)
            .retrieve()
            .bodyToMono(AiServerResponseDto[].class)
            .block();

        List<AiServerResponseDto> responseList = Arrays.asList(responseArray);
        return ListResponse.create(responseList);
    }

}
