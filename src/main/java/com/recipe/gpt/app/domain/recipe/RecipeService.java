package com.recipe.gpt.app.domain.recipe;

import com.recipe.gpt.app.web.dto.recipe.ai.OpenAiRequestDto;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    @Value("${spring.openai.api-key}")
    private String key;

    public ResponseEntity<?> recipeQuery(OpenAiRequestDto body) {

        OpenAiService service = new OpenAiService(
            key);
        CompletionRequest completionRequest = CompletionRequest.builder()
            .prompt(body.getPrompt())
            .model("text-davinci-003")
            .echo(false)
            .build();
        return ResponseEntity.ok(service.createCompletion(completionRequest).getChoices());

    }

}
