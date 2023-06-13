package com.recipe.gpt.app.domain.board;

import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.domain.member.MemberService;
import com.recipe.gpt.app.domain.recipe.RecipeService;
import com.recipe.gpt.app.web.dto.board.BoardDetailResponseDto;
import com.recipe.gpt.app.web.dto.board.BoardIdResponseDto;
import com.recipe.gpt.app.web.dto.board.BoardRequestDto;
import com.recipe.gpt.app.web.dto.board.BoardResponseDto;
import com.recipe.gpt.app.web.dto.board.PaginationRequestDto;
import com.recipe.gpt.app.web.dto.board.search.SearchBoardFilterRequestDto;
import com.recipe.gpt.app.web.response.PagedResponse;
import com.recipe.gpt.app.web.response.Pagination;
import com.recipe.gpt.common.config.security.context.LoginMember;
import com.recipe.gpt.common.exception.BoardNotFoundException;
import com.recipe.gpt.common.exception.NotPossibleToAccessBoardException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberService memberService;

    private final RecipeService recipeService;

    private final AmazonS3Service s3UploadService;

    private final BoardRepository boardRepository;

    /**
     * 게시글 등록
     */
    @Transactional
    public BoardIdResponseDto create(LoginMember loginMember, MultipartFile image, BoardRequestDto body) {
        Member member = memberService.findLoginMember(loginMember);

        String saveFileName = s3UploadService.upload(image);

        Board newBoard = body.toBoard(member, saveFileName);
        Board board = boardRepository.save(newBoard);

        recipeService.create(body.getRecipe(), newBoard);
        return BoardIdResponseDto.of(board.getId());
    }

    /**
     * 게시글 업데이트
     */
    public void updateBoard(LoginMember loginMember, Long id, MultipartFile image, BoardRequestDto body) {
        Member member = memberService.findLoginMember(loginMember);
        Board board = findById(id);

        if (!board.isAccessibleToBoard(member)) {
            throw new NotPossibleToAccessBoardException();
        }

        String saveFileName = s3UploadService.upload(image);

        Board requestBoard = body.toBoard(member, saveFileName);
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

    /**
     * 게시글 필터 조회
     */
    @Transactional(readOnly = true)
    public PagedResponse<BoardResponseDto> findBoardsBySearch(LoginMember loginMember,
        PaginationRequestDto paginationRequestDto,
        SearchBoardFilterRequestDto searchBoardFilterRequestDto) {
        Member member = memberService.findLoginMember(loginMember);

        Pagination pagination = Pagination.create(paginationRequestDto.getPage(),
            paginationRequestDto.getSize());
        PageRequest pageRequest = pagination.toPageRequest();

        Page<Board> boardListBySearch = boardRepository.findBoardListBySearch(
            searchBoardFilterRequestDto, pageRequest);
        return BoardResponseDto.pagedListOf(pagination, boardListBySearch, member);
    }

    /**
     * 레시피 게시글 추천 조회
     */
    public PagedResponse<BoardResponseDto> findRecommendedBoards(LoginMember loginMember,
        Integer page, Integer size) {
        Member member = memberService.findLoginMember(loginMember);

        Pagination pagination = Pagination.create(page, size);
        PageRequest pageRequest = pagination.toPageRequest();

        Page<Board> recommendBoardList = boardRepository.findRecommendedBoardList(pageRequest);
        return BoardResponseDto.pagedListOf(pagination, recommendBoardList, member);
    }

    /**
     * 최근 떠오르는 레시피 게시글 조회
     */
    @Transactional(readOnly = true)
    public PagedResponse<BoardResponseDto> findTrendBoards(LoginMember loginMember, Integer page,
        Integer size) {
        Member member = memberService.findLoginMember(loginMember);

        Pagination pagination = Pagination.create(page, size);
        PageRequest pageRequest = pagination.toPageRequest();

        Page<Board> trendBoardList = boardRepository.findTrendBoardList(pageRequest);
        return BoardResponseDto.pagedListOf(pagination, trendBoardList, member);
    }

    /**
     * 레시피 게시글 상세 조회
     */
    @Transactional
    public BoardDetailResponseDto findBoardDetail(LoginMember loginMember, Long id) {
        Member member = memberService.findLoginMember(loginMember);
        Board board = findById(id);

        // [1] 조회수 증가
        board.updateViews();

        // [2] BoardDetail 반환
        return BoardDetailResponseDto.of(board, member);
    }

    @Transactional(readOnly = true)
    public Board findById(Long id) {
        return boardRepository.findById(id)
            .orElseThrow(BoardNotFoundException::new);
    }

}
