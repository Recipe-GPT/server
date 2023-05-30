package com.recipe.gpt.app.web.controller.chat.chatroom;

import com.recipe.gpt.app.domain.chat.chatroom.ChatRoomService;
import com.recipe.gpt.app.web.dto.chat.chatroom.ChatRoomRequestDto;
import com.recipe.gpt.app.web.dto.chat.chatroom.ChatRoomResponseDto;
import com.recipe.gpt.app.web.path.ApiPath;
import com.recipe.gpt.app.web.response.ListResponse;
import com.recipe.gpt.common.config.security.context.LoginMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "채팅방")
@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "채팅방 생성")
    @PostMapping(ApiPath.CHATROOM_CREATE)
    public ResponseEntity<Void> createChatRoom(
        @AuthenticationPrincipal LoginMember loginMember,
        @Valid @RequestBody ChatRoomRequestDto body) {
        Long chatRoomId = chatRoomService.create(loginMember, body);
        return ResponseEntity.created(URI.create(ApiPath.CHATROOM_CREATE + chatRoomId)).build();
    }

    @Operation(summary = "내 채팅방 조회")
    @GetMapping(ApiPath.CHATROOM_FIND)
    public ListResponse<ChatRoomResponseDto> findMyChatRoom(
        @AuthenticationPrincipal LoginMember loginMember) {
        return chatRoomService.findMyChatRoom(loginMember);
    }

    @Operation(summary = "채팅방 수정")
    @PutMapping(ApiPath.CHATROOM_UPDATE)
    public ResponseEntity<Void> updateChatRoom(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long id,
        @Valid @RequestBody ChatRoomRequestDto body) {
        chatRoomService.updateChatRoom(loginMember, id, body);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "채팅방 삭제")
    @DeleteMapping(ApiPath.CHATROOM_DELETE)
    public ResponseEntity<Void> deleteChatRoom(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long id
    ) {
        chatRoomService.deleteChatRoom(loginMember, id);
        return ResponseEntity.ok().build();
    }

}
