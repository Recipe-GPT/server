package com.recipe.gpt.app.web.dto.member;

import com.recipe.gpt.app.domain.member.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FindMemberSelfResponseDto {

    private Long id;

    private String email;

    private String imageUrl;

    private String name;

    public static FindMemberSelfResponseDto of(Member member) {
        return new FindMemberSelfResponseDto(
            member.getId(),
            member.getEmail(),
            member.getImageUrl(),
            member.getName()
        );
    }

}
