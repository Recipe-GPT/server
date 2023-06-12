package com.recipe.gpt.app.domain.board;

import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.domain.member.MemberService;
import com.recipe.gpt.app.domain.recipe.RecipeService;
import com.recipe.gpt.app.web.dto.board.BoardIdResponseDto;
import com.recipe.gpt.app.web.dto.board.BoardUploadRequestDto;
import com.recipe.gpt.common.config.security.context.LoginMember;
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
    public BoardIdResponseDto create(LoginMember loginMember, BoardUploadRequestDto body) {
        Member member = memberService.findLoginMember(loginMember);

        Board newBoard = body.toBoard(member);
        Board board = boardRepository.save(newBoard);

        recipeService.create(body.getRecipe(), newBoard);
        return BoardIdResponseDto.of(board.getId());
    }

}
