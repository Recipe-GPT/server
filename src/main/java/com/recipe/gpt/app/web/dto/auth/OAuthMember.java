package com.recipe.gpt.app.web.dto.auth;

import com.recipe.gpt.app.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class OAuthMember {

    private final String email;
    private final String name;
    private final String profileImageUrl;
    private final String refreshToken;

    public Member toMember() {
        return Member.builder()
            .name(name)
            .email(email)
            .imageUrl(profileImageUrl)
            .build();
    }

}
