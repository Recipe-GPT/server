package com.recipe.gpt.app.web.dto.board;

import com.recipe.gpt.app.domain.board.Board;
import com.recipe.gpt.app.domain.board.Difficulty;
import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.domain.recipe.Recipe;
import com.recipe.gpt.common.util.DateUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class BoardDetailResponseDto {

    private Long boardId;

    private String name;

    private String description;

    private String imageUrl;

    private Difficulty difficulty;

    private Long time;

    private Long serving;

    private Long views;

    private Boolean editable;

    private BoardRecipeResponseDto recipe;

    private String writer;

    private String createdDate;

    public static BoardDetailResponseDto of(Board board, Member member) {
        Recipe recipe = board.getRecipe();

        return new BoardDetailResponseDto(
            board.getId(),
            recipe.getName(),
            recipe.getDescription(),
            board.getImageUrl(),
            board.getDifficulty(),
            board.getTime(),
            board.getServing(),
            board.getViews(),
            board.isAccessibleToBoard(member),
            BoardRecipeResponseDto.of(recipe),
            board.getMember().getName(),
            DateUtils.formatToDotSeparatedDate(board.getCreateDate())
        );
    }

}
