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

        final String prompt = """
            An recipe recommendation assistant that recommends three delicious food recipes with given ingredients. The recommendation assistant responds only in JSON key-value format. It will not provide any other unnecessary responses.
            USER:
            [ingredients]
            Cheddar cheese, Bread, Pork Belly, Rice, Cabbage, Scallions, Potatoes, Eggs, Bracken, Onions
            [condiments]
            Pepper, Salt, Soy sauce, Gochujang, Sugar, Mustard, Ketchup
            Assistant:
            [{
              "name": "Kimchi Pork Belly Sandwich",
              "description": "A delicious sandwich with kimchi and pork belly",
              "ingredients": ["bread", "pork belly", "kimchi", "cheddar cheese"],
              "condiments": ["mustard", "ketchup"],
              "cooking_time_second": 900
            },
            {
              "name": "Cheese Pork Belly Rice Balls",
              "description": "A delicious rice ball made with pork belly and cheddar cheese",
              "ingredients": ["rice", "pork belly", "cheddar cheese", "green onion"],
              "condiments": ["gochujang", "soy sauce", "sugar"],
              "cooking_time_second": 1200
            },
            {
              "name": "Egg roll",
              "description": "A food made by spreading an egg wide and rolling it up",
              "ingredients": ["egg", "scallion"],
              "condiments": ["salt", "pepper"],
              "cooking_time_second": 600
            }]</s>
            USER:
            [ingredients]
            Mozzarella cheese, Pork belly, Bread, Rice, Cabbage, Scallions, Potatoes, Fish, Eggs
            [condiments]
            Pepper, Gochujang, Salt, Soy Sauce, Bulgogi Sauce, Gochujang, Sugar, Mayonnaise, Ketchup
            Assistant:""";

        OpenAiService service = new OpenAiService(
            key);
        CompletionRequest completionRequest = CompletionRequest.builder()
            .prompt(prompt)
            .model("text-davinci-003")
            .echo(false)
            .bestOf(1)
            .frequencyPenalty(0D)
            .logprobs(0)
            .maxTokens(120)
            .presencePenalty(0D)
            .temperature(0.3)
            .topP(1D)
            .build();
        return ResponseEntity.ok(service.createCompletion(completionRequest).getChoices());

    }

}
