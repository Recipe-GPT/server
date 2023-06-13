package com.recipe.gpt.app.web.dto.board.search;

import com.recipe.gpt.app.domain.board.BoardSortType;
import com.recipe.gpt.common.validator.ValidEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SearchBoardFilterRequestDto {

    private String search;

    @ValidEnum(enumClass = BoardSortType.class)
    private BoardSortType sortType;

}
