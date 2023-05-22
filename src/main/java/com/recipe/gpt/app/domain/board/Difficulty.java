package com.recipe.gpt.app.domain.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Difficulty {

    VERY_EASY("VERY_EASY", "아주 쉬움"),
    EASY("EASY", "쉬움"),
    MEDIUM("MEDIUM", "보통"),
    HARD("HARD", "어려움"),
    VERY_HARD("VERY_HARD", "아주 어려움");

    private final String name;
    private final String description;

}

