package com.recipe.gpt.app.domain.search.seasoning;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasoningDataRepository extends JpaRepository<SeasoningData, Long> {

    List<SeasoningData> findByNameContains(String name);

}
