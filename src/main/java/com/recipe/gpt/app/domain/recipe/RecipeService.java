package com.recipe.gpt.app.domain.recipe;

import com.recipe.gpt.app.domain.board.Board;
import com.recipe.gpt.app.domain.chat.Chat;
import com.recipe.gpt.app.domain.recipe.ingredient.IngredientItem;
import com.recipe.gpt.app.domain.recipe.procedure.ProcedureItem;
import com.recipe.gpt.app.domain.recipe.seasoning.SeasoningItem;
import com.recipe.gpt.app.web.dto.ai.AiServerRecipeRequestDto;
import com.recipe.gpt.app.web.dto.ai.AiServerRecommendResponseDto;
import com.recipe.gpt.app.web.dto.ai.ExtractedRecipeResponseDto;
import com.recipe.gpt.app.web.dto.recipe.RecipeRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    /**
     * 레시피 생성
     */
    @Transactional
    public void create(RecipeRequestDto body, Board board) {
        Recipe recipe = body.toRecipe();
        setRecipeIngredients(recipe, body.toIngredientItems());
        setRecipeSeasonings(recipe, body.toSeasoningItems());
        setRecipeProcedures(recipe, body.toProcedureItems());
        setBoard(recipe, board);
        recipeRepository.save(recipe);
    }

    /**
     * ai server | 요리 추천 질문 응답으로 레시피 생성
     */
    @Transactional
    public void createByRecommendQueryResponse(AiServerRecommendResponseDto body, Chat chat) {
        Recipe recipe = body.toRecipe();
        setRecipeIngredients(recipe, body.toIngredientItems());
        setRecipeSeasonings(recipe, body.toSeasoningItems());
        setChat(recipe, chat);
        recipeRepository.save(recipe);
    }


    /**
     * ai server | 레시피 질문 응답으로 레시피 생성
     */
    @Transactional
    public void createByRecipeQueryResponse(AiServerRecipeRequestDto body,
        ExtractedRecipeResponseDto response, Chat latestChat) {
        Recipe recipe = body.toRecipe();
        setRecipeIngredients(recipe, response.toIngredientItems());
        setRecipeSeasonings(recipe, response.toSeasoningItems());
        setRecipeProcedures(recipe, response.toProcedureItems());
        setChat(recipe, latestChat);
        recipeRepository.save(recipe);
    }

    private void setRecipeIngredients(Recipe recipe, List<IngredientItem> ingredients) {
        ingredients.forEach(ingredient -> ingredient.setRecipe(recipe));
    }

    private void setRecipeSeasonings(Recipe recipe, List<SeasoningItem> seasonings) {
        seasonings.forEach(seasoning -> seasoning.setRecipe(recipe));
    }

    private void setRecipeProcedures(Recipe recipe, List<ProcedureItem> procedures) {
        procedures.forEach(procedure -> procedure.setRecipe(recipe));
    }

    private void setChat(Recipe recipe, Chat latestChat) {
        recipe.setChat(latestChat);
    }

    private void setBoard(Recipe recipe, Board board) {
        recipe.setBoard(board);
    }

}