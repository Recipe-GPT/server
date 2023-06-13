package com.recipe.gpt.app.web.dto.board;

import com.recipe.gpt.app.domain.board.Board;
import com.recipe.gpt.app.domain.board.Difficulty;
import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.web.dto.recipe.RecipeRequestDto;
import com.recipe.gpt.common.validator.ValidEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class BoardRequestDto {

    @ValidEnum(enumClass = Difficulty.class)
    private Difficulty difficulty;

    private long serving;

    private long time;

    @NotNull
    private RecipeRequestDto recipe;

    // TODO::이미지 URL
    public Board toBoard(Member member) {
        return Board.builder()
            .member(member)
            .serving(serving)
            .time(time)
            .difficulty(difficulty)
            .imageUrl("example")
            .views(0L)
            .build();
    }

}
