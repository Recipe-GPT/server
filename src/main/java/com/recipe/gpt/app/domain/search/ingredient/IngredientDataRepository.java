package com.recipe.gpt.app.domain.search.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientDataRepository extends JpaRepository<IngredientData, Long> {

    List<IngredientData> findByNameContains(String name);
}
