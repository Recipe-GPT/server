package com.recipe.gpt.app.domain.recipe;

import com.recipe.gpt.app.domain.chat.Chat;
import com.recipe.gpt.app.domain.recipe.ingredient.IngredientItem;
import com.recipe.gpt.app.domain.recipe.procedure.ProcedureItem;
import com.recipe.gpt.app.domain.recipe.seasoning.SeasoningItem;
import com.recipe.gpt.app.web.dto.ai.AiServerRecipeRequestDto;
import com.recipe.gpt.app.web.dto.ai.ExtractedRecipeResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    /**
     * ai server 응답으로 레시피 생성
     */
    @Transactional
    public void saveByAiServerResponse(AiServerRecipeRequestDto body,
        ExtractedRecipeResponseDto response, Chat latestChat) {
        Recipe recipe = body.toRecipe();

        List<IngredientItem> ingredientItems = response.toIngredientItems();
        List<SeasoningItem> seasoningItems = response.toSeasoningItems();
        List<ProcedureItem> procedureItems = response.toProcedureItems();

        setRecipe(recipe, ingredientItems, seasoningItems, procedureItems);
        setChat(recipe, latestChat);
        recipeRepository.save(recipe);
    }

    private void setRecipe(Recipe recipe,
        List<IngredientItem> ingredientItems,
        List<SeasoningItem> seasoningItems,
        List<ProcedureItem> procedureItems) {
        for (IngredientItem ingredientItem : ingredientItems) {
            ingredientItem.setRecipe(recipe);
        }
        for (SeasoningItem seasoningItem : seasoningItems) {
            seasoningItem.setRecipe(recipe);
        }
        for (ProcedureItem procedureItem : procedureItems) {
            procedureItem.setRecipe(recipe);
        }
    }

    public void setChat(Recipe recipe, Chat latestChat) {
        recipe.setChat(latestChat);
    }

}
