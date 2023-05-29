package com.recipe.gpt.app.domain.search.seasoning;

import com.recipe.gpt.app.web.dto.search.SeasoningDataResponseDto;
import com.recipe.gpt.app.web.response.ListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasoningDataService {

    private final SeasoningDataRepository seasoningDataRepository;

    @Transactional(readOnly = true)
    public ListResponse<SeasoningDataResponseDto> searchSeasoning(String query) {
        List<SeasoningDataResponseDto> responseList = seasoningDataRepository.findByNameContains(query).stream()
                .map(SeasoningDataResponseDto::of)
                .toList();
        return ListResponse.create(responseList);
    }
}