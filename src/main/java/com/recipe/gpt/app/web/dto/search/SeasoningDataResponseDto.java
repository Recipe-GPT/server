package com.recipe.gpt.app.web.dto.search;

import com.recipe.gpt.app.domain.search.seasoning.SeasoningData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SeasoningDataResponseDto {

    private Long id;

    private String name;

    public static SeasoningDataResponseDto of(SeasoningData seasoningData) {
        return new SeasoningDataResponseDto(
                seasoningData.getId(),
                seasoningData.getName()
        );
    }

}
