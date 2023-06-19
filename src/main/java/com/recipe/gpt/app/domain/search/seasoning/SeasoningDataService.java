package com.recipe.gpt.app.domain.search.seasoning;

import com.recipe.gpt.app.web.dto.search.SeasoningDataResponseDto;
import com.recipe.gpt.app.web.response.ListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeasoningDataService {

    private final SeasoningDataRepository seasoningDataRepository;

    @Transactional(readOnly = true)
    public ListResponse<SeasoningDataResponseDto> searchSeasoning(String query) {
        List<SeasoningData> seasoningList = seasoningDataRepository.findByNameContains(query);
        return ListResponse.of(
            SeasoningDataResponseDto.listOf(seasoningList)
        );
    }

}
