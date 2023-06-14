package com.recipe.gpt.app.web.dto.board;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class BoardIdResponseDto {

    private Long boardId;

    public static BoardIdResponseDto of(Long boardId) {
        return new BoardIdResponseDto(boardId);
    }

}
