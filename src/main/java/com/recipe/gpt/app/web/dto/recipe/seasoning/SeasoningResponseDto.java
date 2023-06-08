package com.recipe.gpt.app.web.dto.recipe.seasoning;

import com.recipe.gpt.app.domain.recipe.seasoning.Seasoning;
import com.recipe.gpt.app.domain.recipe.seasoning.SeasoningItem;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SeasoningResponseDto {

    private String name;

    private String quantity;

    private static SeasoningResponseDto of(SeasoningItem seasoningItem) {
        return new SeasoningResponseDto(
            seasoningItem.getName(),
            seasoningItem.getQuantity()
        );
    }

    public static List<SeasoningResponseDto> listOf(Seasoning seasoning) {
        return seasoning.getSeasoningItems().stream()
            .map(SeasoningResponseDto::of)
            .collect(Collectors.toList());
    }

}
