package com.recipe.gpt.app.domain.search.ingredient.repository;

import com.recipe.gpt.app.domain.search.ingredient.IngredientData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientDataRepository extends JpaRepository <IngredientData, Long> {

    List<IngredientData> findByNameContains(String name);
}
