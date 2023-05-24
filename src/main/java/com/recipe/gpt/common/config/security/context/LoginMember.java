package com.recipe.gpt.common.config.security.context;

import lombok.Getter;

@Getter
public class LoginMember {
    private final Long id;
    private final String email;

    private LoginMember(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public static LoginMember create(Long id, String email) {
        return new LoginMember(id, email);
    }
}
