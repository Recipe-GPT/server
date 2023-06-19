package com.recipe.gpt.app.domain.search.ingredient;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientDataRepository extends JpaRepository<IngredientData, Long> {

    List<IngredientData> findByNameContains(String name);

}
