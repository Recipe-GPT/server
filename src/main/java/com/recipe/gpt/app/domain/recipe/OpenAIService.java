package com.recipe.gpt.app.domain.recipe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.recipe.gpt.app.web.dto.recipe.openAi.EventData;
import com.recipe.gpt.app.web.dto.recipe.openAi.OpenAiRequestDto;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class OpenAIService {

    private final String prompt = """
        example prompt
        """;

    private String openAiUrl = "";

    private String openAiKey = "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";

    private WebClient client;

    private final ObjectMapper objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    @PostConstruct
    public void init() {
        client = WebClient.builder()
            .baseUrl(openAiUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("api-key", openAiKey)
            .build();
    }

    public Flux<String> getData() throws JsonProcessingException {
        OpenAiRequestDto request = new OpenAiRequestDto();
        request.setPrompt(prompt);
        request.setMaxTokens(2048);
        request.setTemperature(1.0);
        request.setFrequencyPenalty(0.0);
        request.setPresencePenalty(0.0);
        request.setTopP(0.5);
        request.setBestOf(1);
        request.setStream(true);
        request.setStop(null);

        String requestValue = objectMapper.writeValueAsString(request);

        return client.post()
            .bodyValue(requestValue)
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(String.class)
            .mapNotNull(event -> {
                try {
                    String jsonData = event.substring(event.indexOf("{"), event.lastIndexOf("}") + 1);
                    return objectMapper.readValue(jsonData, EventData.class);
                } catch (JsonProcessingException | StringIndexOutOfBoundsException e) {
                    return null;
                }
            })
            .skipUntil(event -> !event.getChoices().get(0).getText().equals("\n"))
            .map(event -> event.getChoices().get(0).getText());
    }

}
