package com.recipe.gpt.app.web.dto.board.search;

import com.recipe.gpt.app.web.dto.board.PaginationRequestDto;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SearchBoardRequestDto {

    @NotNull
    private PaginationRequestDto pagination;

    @NotNull
    private SearchBoardFilterRequestDto filter;

}
