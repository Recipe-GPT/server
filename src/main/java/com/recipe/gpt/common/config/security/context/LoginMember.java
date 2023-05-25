package com.recipe.gpt.common.config.security.context;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginMember {

    private final Long id;

    private final String email;

    public static LoginMember of(Long id, String email) {
        return new LoginMember(id, email);
    }

}
