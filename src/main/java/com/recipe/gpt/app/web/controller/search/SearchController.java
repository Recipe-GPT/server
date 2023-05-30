package com.recipe.gpt.app.web.controller.search;

import com.recipe.gpt.app.domain.search.ingredient.IngredientDataService;
import com.recipe.gpt.app.domain.search.seasoning.SeasoningDataService;
import com.recipe.gpt.app.web.dto.search.IngredientDataResponseDto;
import com.recipe.gpt.app.web.dto.search.SeasoningDataResponseDto;
import com.recipe.gpt.app.web.path.ApiPath;
import com.recipe.gpt.app.web.response.ListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "검색")
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final IngredientDataService ingredientDataService;
    private final SeasoningDataService seasoningDataService;

    @Operation(summary = "식재료 검색")
    @GetMapping(ApiPath.SEARCH_INGREDIENT)
    public ResponseEntity<ListResponse<IngredientDataResponseDto>> searchIngredient(
        @RequestParam String query
    ) {
        return ResponseEntity.ok(ingredientDataService.searchIngredient(query));
    }

    @Operation(summary = "양념 검색")
    @GetMapping(ApiPath.SEARCH_SEASONING)
    public ResponseEntity<ListResponse<SeasoningDataResponseDto>> searchSeasoning(
        @RequestParam String query
    ) {
        return ResponseEntity.ok(seasoningDataService.searchSeasoning(query));
    }

}
