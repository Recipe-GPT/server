package com.recipe.gpt.app.web.dto.recipe.ai;

import com.recipe.gpt.common.util.StringUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ItemResponseDto {

    private String name;

    private String quantity;

    public static ItemResponseDto of(String item) {
        return new ItemResponseDto(
            StringUtils.extractStringOutsideParentheses(item),
            StringUtils.extractStringInsideParentheses(item)
        );
    }

    public static List<ItemResponseDto> listOf(List<String> items) {
        return items.stream()
            .map(ItemResponseDto::of)
            .collect(Collectors.toList());
    }

}