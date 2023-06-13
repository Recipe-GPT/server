package com.recipe.gpt.app.domain.board;

import com.recipe.gpt.app.web.dto.board.search.SearchBoardFilterRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<Board> findBoardListBySearch(SearchBoardFilterRequestDto filter, Pageable pageable);

    Page<Board> findTrendBoardList(Pageable pageable);

    Page<Board> findRecommendedBoardList(Pageable pageable);
}
