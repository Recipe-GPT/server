package com.recipe.gpt.app.domain.search.seasoning;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeasoningDataRepository extends JpaRepository<SeasoningData, Long> {

    List<SeasoningData> findByNameContains(String name);
}
