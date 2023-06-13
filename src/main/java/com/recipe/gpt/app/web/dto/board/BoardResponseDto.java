package com.recipe.gpt.app.web.dto.board;

import com.recipe.gpt.app.domain.board.Board;
import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.web.response.PagedResponse;
import com.recipe.gpt.app.web.response.Pagination;
import com.recipe.gpt.common.util.DateUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class BoardResponseDto {

    private Long boardId;

    private String name;

    private String description;

    private String imageUrl;

    private String writer;

    private String createdDate;

    private Boolean editable;

    private Long views;

    public static BoardResponseDto of(Board board, Member member) {
        return new BoardResponseDto(
            board.getId(),
            board.getRecipe().getName(),
            board.getRecipe().getDescription(),
            board.getImageUrl(),
            board.getMember().getName(),
            DateUtils.formatToHyphenSeparatedDate(board.getCreateDate()),
            board.isAccessibleToBoard(member),
            board.getViews()
        );
    }

    public static List<BoardResponseDto> listOf(List<Board> boards, Member member) {
        return boards.stream()
            .map(board -> BoardResponseDto.of(board, member))
            .collect(Collectors.toList());
    }

    public static PagedResponse<BoardResponseDto> pagedListOf(Pagination pagination,
        Page<Board> boards, Member member) {
        List<BoardResponseDto> boardResponseDtoList = boards.stream()
            .map(board -> BoardResponseDto.of(board, member))
            .toList();

        pagination.setTotalCount(boards.getTotalElements());
        pagination.setTotalPages(boards.getTotalPages());

        return PagedResponse.of(
            pagination,
            boardResponseDtoList
        );
    }

}