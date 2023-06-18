package com.recipe.gpt.app.domain.recipe;

import com.recipe.gpt.app.domain.board.Board;
import com.recipe.gpt.app.domain.chat.Chat;
import com.recipe.gpt.app.domain.recipe.ingredient.IngredientItem;
import com.recipe.gpt.app.domain.recipe.procedure.ProcedureItem;
import com.recipe.gpt.app.domain.recipe.seasoning.SeasoningItem;
import com.recipe.gpt.app.web.dto.ai.AiServerRecommendResponseDto;
import com.recipe.gpt.app.web.dto.ai.ExtractedRecipeResponseDto;
import com.recipe.gpt.app.web.dto.recipe.RecipeRequestDto;
import com.recipe.gpt.common.exception.RecipeNotFoundException;
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
     * ai server 응답으로 레시피 생성
     */
    @Transactional
    public Recipe createByRecommendQueryResponse(AiServerRecommendResponseDto body, Chat chat) {
        Recipe recipe = body.toRecipe();
        setRecipeIngredients(recipe, body.toIngredientItems());
        setRecipeSeasonings(recipe, body.toSeasoningItems());
        setChat(recipe, chat);
        return recipeRepository.save(recipe);
    }

    /**
     * 레시피 업데이트
     */
    @Transactional
    public void updateRecipe(Recipe recipe, ExtractedRecipeResponseDto response) {
        recipe.updateIsSelected();
        recipe.update(response.toIngredientItems(),
            response.toSeasoningItems(),
            response.toProcedureItems());

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

    @Transactional(readOnly = true)
    public Recipe findById(Long id) {
        return recipeRepository.findById(id)
            .orElseThrow(RecipeNotFoundException::new);
    }

}