package com.recipe.gpt.app.domain.recipe;


import com.recipe.gpt.app.web.dto.recipe.ai.AiServerRequestDto;
import com.recipe.gpt.app.web.dto.recipe.ai.AiServerResponseDto;
import com.recipe.gpt.app.web.response.ListResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class RecipeService {

    @Value("${spring.openai.api-key}")
    private String apiKey;

    public ListResponse<AiServerResponseDto> recipeQuery(AiServerRequestDto body) {

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("ingredients", body.getIngredients());
        bodyMap.put("seasonings", body.getSeasonings());

        WebClient webClient = WebClient
            .builder()
            .baseUrl("https://recipe-api.bssm.kro.kr/")
            .defaultHeader("x-api-key", apiKey)
            .build();

        AiServerResponseDto[] responseArray = webClient
            .post()
            .uri("generate/proxy")
            .bodyValue(bodyMap)
            .retrieve()
            .bodyToMono(AiServerResponseDto[].class)
            .block();

        List<AiServerResponseDto> responseList = Arrays.asList(responseArray);
        return ListResponse.create(responseList);
    }

}
