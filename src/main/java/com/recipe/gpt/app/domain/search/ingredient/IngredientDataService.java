package com.recipe.gpt.app.domain.search.ingredient;

import com.recipe.gpt.app.web.dto.search.IngredientDataResponseDto;
import com.recipe.gpt.app.web.response.ListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientDataService {

    private final IngredientDataRepository ingredientDataRepository;

    @Transactional(readOnly = true)
    public ListResponse<IngredientDataResponseDto> searchIngredient(String query) {
        List<IngredientData> ingredientList = ingredientDataRepository.findByNameContains(query);
        return ListResponse.create(
                IngredientDataResponseDto.listOf(ingredientList)
        );
    }
}
