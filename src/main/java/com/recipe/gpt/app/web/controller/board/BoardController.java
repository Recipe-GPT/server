package com.recipe.gpt.app.web.controller.board;

import com.recipe.gpt.app.domain.board.BoardService;
import com.recipe.gpt.app.web.dto.board.BoardIdResponseDto;
import com.recipe.gpt.app.web.dto.board.BoardUploadRequestDto;
import com.recipe.gpt.app.web.path.ApiPath;
import com.recipe.gpt.common.config.security.context.LoginMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Operation(summary = "레시피 업로드")
    @PostMapping(ApiPath.BOARD_UPLOAD)
    public ResponseEntity<BoardIdResponseDto> uploadBoard(
        @AuthenticationPrincipal LoginMember loginMember,
        @RequestPart(value = "image") MultipartFile image,
        @Valid @RequestPart(value = "rq") BoardUploadRequestDto body
    ) {
        return ResponseEntity.ok(boardService.create(loginMember, body));
    }

}
