package com.recipe.gpt.app.web.dto.search;

import com.recipe.gpt.app.domain.search.seasoning.SeasoningData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SeasoningDataResponseDto {

    @Schema(description = "양념 고유 id")
    private Long id;

    @Schema(description = "양념 이름")
    private String name;

    public static SeasoningDataResponseDto of(SeasoningData seasoningData) {
        return new SeasoningDataResponseDto(
                seasoningData.getId(),
                seasoningData.getName()
        );
    }

}
