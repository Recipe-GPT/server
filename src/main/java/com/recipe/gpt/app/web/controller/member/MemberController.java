package com.recipe.gpt.app.web.controller.member;

import com.recipe.gpt.app.domain.member.MemberService;
import com.recipe.gpt.app.web.dto.member.FindMemberSelfResponseDto;
import com.recipe.gpt.app.web.path.ApiPath;
import com.recipe.gpt.common.config.security.context.LoginMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "내 정보 불러오기")
    @GetMapping(ApiPath.MEMBER)
    public ResponseEntity<FindMemberSelfResponseDto> findMemberSelf(
        @AuthenticationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok(memberService.findMemberSelf(loginMember));
    }

}
