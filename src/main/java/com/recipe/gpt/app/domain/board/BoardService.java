package com.recipe.gpt.app.domain.board;

import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.domain.member.MemberService;
import com.recipe.gpt.app.domain.recipe.RecipeService;
import com.recipe.gpt.app.web.dto.board.BoardIdResponseDto;
import com.recipe.gpt.app.web.dto.board.BoardRequestDto;
import com.recipe.gpt.common.config.security.context.LoginMember;
import com.recipe.gpt.common.exception.BoardNotFoundException;
import com.recipe.gpt.common.exception.NotPossibleToAccessBoardException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberService memberService;

    private final RecipeService recipeService;

    private final BoardRepository boardRepository;

    /**
     * 게시글 등록
     */
    @Transactional
    public BoardIdResponseDto create(LoginMember loginMember, BoardRequestDto body) {
        Member member = memberService.findLoginMember(loginMember);

        Board newBoard = body.toBoard(member);
        Board board = boardRepository.save(newBoard);

        recipeService.create(body.getRecipe(), newBoard);
        return BoardIdResponseDto.of(board.getId());
    }

    /**
     * 게시글 업데이트
     */
    public void updateBoard(LoginMember loginMember, Long id, BoardRequestDto body) {
        Member member = memberService.findLoginMember(loginMember);
        Board board = findById(id);

        if (!board.isAccessibleToBoard(member)) {
            throw new NotPossibleToAccessBoardException();
        }

        Board requestBoard = body.toBoard(member);
        board.update(requestBoard);

        recipeService.create(body.getRecipe(), board);
    }

    /**
     * 게시글 삭제
     */
    public void deleteBoard(LoginMember loginMember, Long id) {
        Member member = memberService.findLoginMember(loginMember);
        Board board = findById(id);

        if (!board.isAccessibleToBoard(member)) {
            throw new NotPossibleToAccessBoardException();
        }

        boardRepository.delete(board);
    }

    @Transactional(readOnly = true)
    public Board findById(Long id) {
        return boardRepository.findById(id)
            .orElseThrow(BoardNotFoundException::new);
    }

}
