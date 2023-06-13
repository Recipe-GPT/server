package com.recipe.gpt.app.web.controller.board;

import com.recipe.gpt.app.domain.board.BoardService;
import com.recipe.gpt.app.web.dto.board.BoardDetailResponseDto;
import com.recipe.gpt.app.web.dto.board.BoardIdResponseDto;
import com.recipe.gpt.app.web.dto.board.BoardRequestDto;
import com.recipe.gpt.app.web.dto.board.BoardResponseDto;
import com.recipe.gpt.app.web.dto.board.search.SearchBoardRequestDto;
import com.recipe.gpt.app.web.path.ApiPath;
import com.recipe.gpt.app.web.response.ListResponse;
import com.recipe.gpt.app.web.response.PagedResponse;
import com.recipe.gpt.common.config.security.context.LoginMember;
import com.recipe.gpt.common.util.FileValidateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "커뮤니티")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "레시피 게시글 업로드")
    @PostMapping(value = ApiPath.BOARD_UPLOAD)
    public ResponseEntity<BoardIdResponseDto> uploadBoard(
        @AuthenticationPrincipal LoginMember loginMember,
        @RequestPart(value = "image") MultipartFile image,
        @Valid @RequestPart(value = "rq") BoardRequestDto body
    ) {
        FileValidateUtils.imageValidationCheck(image);
        return ResponseEntity.ok(boardService.create(loginMember, image, body));
    }

    @Operation(summary = "레시피 게시글 수정")
    @PutMapping(ApiPath.BOARD_UPDATE)
    public ResponseEntity<Void> updateBoard(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long id,
        @RequestPart(value = "image") MultipartFile image,
        @Valid @RequestPart(value = "rq") BoardRequestDto body
    ) {
        FileValidateUtils.imageValidationCheck(image);
        boardService.updateBoard(loginMember, id, image, body);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "레시피 게시글 삭제")
    @DeleteMapping(ApiPath.BOARD_DELETE)
    public ResponseEntity<Void> deleteBoard(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long id
    ) {
        boardService.deleteBoard(loginMember, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "필터 검색")
    @GetMapping(ApiPath.BOARD_VIEW_FILTER)
    public ResponseEntity<PagedResponse<BoardResponseDto>> findBoardsBySearch(
        @AuthenticationPrincipal LoginMember loginMember,
        @Valid @ParameterObject @ModelAttribute SearchBoardRequestDto body
    ) {
        return ResponseEntity.ok(boardService.findBoardsBySearch(
            loginMember,
            body.getPagination(),
            body.getFilter()
        ));
    }

    @Operation(summary = "레시피 게시글 추천 조회")
    @GetMapping(ApiPath.BOARD_VIEW_RECOMMEND)
    public ResponseEntity<ListResponse<BoardResponseDto>> findRecommendedBoards(
        @AuthenticationPrincipal LoginMember loginMember
    ) {
        return ResponseEntity.ok(boardService.findRecommendedBoards(loginMember));
    }

    @Operation(summary = "최근 떠오르는 레시피 게시글 조회")
    @GetMapping(ApiPath.BOARD_VIEW_TRENDING)
    public ResponseEntity<ListResponse<BoardResponseDto>> findTrendBoards(
        @AuthenticationPrincipal LoginMember loginMember
    ) {
        return ResponseEntity.ok(boardService.findTrendBoards(loginMember));
    }

    @Operation(summary = "레시피 게시글 상세 조회")
    @GetMapping(ApiPath.BOARD_VIEW_DETAIL)
    public ResponseEntity<BoardDetailResponseDto> findBoardDetail(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(boardService.findBoardDetail(loginMember, id));
    }

}
