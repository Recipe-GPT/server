package com.recipe.gpt.app.domain.board;

import static com.recipe.gpt.app.domain.board.QBoard.board;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.recipe.gpt.app.web.dto.board.search.SearchBoardFilterRequestDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Board> findBoardListBySearch(SearchBoardFilterRequestDto filter,
        Pageable pageable) {
        List<Board> contents = jpaQueryFactory
            .selectFrom(board)
            .where(searchEq(filter.getSearch()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(getOrderBy(filter.getSortType()))
            .fetch();

        JPAQuery<Board> countQuery = jpaQueryFactory
            .selectFrom(board)
            .where(searchEq(filter.getSearch()));

        return PageableExecutionUtils.getPage(contents, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public Page<Board> findTrendBoardList(Pageable pageable) {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);

        List<Board> contents = jpaQueryFactory
            .selectFrom(board)
            .where(board.createDate.after(oneWeekAgo)) // 최근 1주 동안
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(getOrderBy(BoardSortType.POPULAR))
            .fetch();

        JPAQuery<Board> countQuery = jpaQueryFactory
            .selectFrom(board)
            .where(board.createDate.after(oneWeekAgo));

        return PageableExecutionUtils.getPage(contents, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public Page<Board> findRecommendedBoardList(Pageable pageable) {
        List<Board> shuffledContents = jpaQueryFactory
            .selectFrom(board)
            .fetch();

        Collections.shuffle(shuffledContents); // 랜덤

        int fromIndex = pageable.getPageNumber() * pageable.getPageSize();
        int toIndex = Math.min(fromIndex + pageable.getPageSize(), shuffledContents.size());
        List<Board> contents = shuffledContents.subList(fromIndex, toIndex);

        return PageableExecutionUtils.getPage(contents, pageable, shuffledContents::size);
    }

    private OrderSpecifier<?>[] getOrderBy(BoardSortType sortType) {
        Order order = Order.DESC;

        List<OrderSpecifier<?>> orderSpecifierList = new ArrayList<>();
        if (Objects.isNull(sortType)) {
            orderSpecifierList.add(new OrderSpecifier<>(Order.DESC, board.views).nullsLast());
            orderSpecifierList.add(new OrderSpecifier<>(Order.DESC, board.createDate).nullsLast());
            return orderSpecifierList.toArray(OrderSpecifier<?>[]::new);
        }

        switch (sortType) {
            case RECENT ->
                orderSpecifierList.add(new OrderSpecifier<>(order, board.createDate).nullsLast());
            case POPULAR ->
                orderSpecifierList.add(new OrderSpecifier<>(order, board.views).nullsLast());
        }

        orderSpecifierList.add(new OrderSpecifier<>(Order.DESC, board.views).nullsLast());
        orderSpecifierList.add(new OrderSpecifier<>(Order.DESC, board.createDate).nullsLast());
        return orderSpecifierList.toArray(OrderSpecifier<?>[]::new);
    }

    private BooleanExpression searchEq(String search) {
        if (search == null) {
            return Expressions.TRUE;
        }
        return board.recipe.name.contains(search);
    }

}
