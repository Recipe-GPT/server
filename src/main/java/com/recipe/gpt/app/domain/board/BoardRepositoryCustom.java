package com.recipe.gpt.app.domain.board;

import com.recipe.gpt.app.web.dto.board.search.SearchBoardFilterRequestDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<Board> findBoardListBySearch(SearchBoardFilterRequestDto filter, Pageable pageable);

    List<Board> findTrendBoardList();

    List<Board> findRecommendedBoardList();
}
