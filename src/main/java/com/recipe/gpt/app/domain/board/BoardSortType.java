package com.recipe.gpt.app.domain.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardSortType {

    POPULAR("POPULAR", "인기순"),
    RECENT("RECENT", "최신순");

    private final String name;
    private final String description;

}