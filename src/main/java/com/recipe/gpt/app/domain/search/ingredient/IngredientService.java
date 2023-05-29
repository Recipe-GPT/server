package com.recipe.gpt.app.domain.search.ingredient;

import com.recipe.gpt.app.domain.search.ingredient.repository.IngredientDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientDataRepository ingredientDataRepository;

    @Transactional(readOnly = true)
    public void searchIngredient(String query) {
        List<IngredientData> ingredientList = ingredientDataRepository.findByNameContains(query);
    }
}
