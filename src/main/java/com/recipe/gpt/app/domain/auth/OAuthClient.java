package com.recipe.gpt.app.domain.auth;

import com.recipe.gpt.app.web.dto.auth.OAuthMember;

public interface OAuthClient {

    OAuthMember getOAuthMember(String code);

}
