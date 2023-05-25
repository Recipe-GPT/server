package com.recipe.gpt.common.config.security.jwt;

public class JwtProperty {

    public static final String SIGN_KEY = "dev-test-key";
    public static final String MEMBER_EMAIL = "MEMBER_EMAIL";
    public static final String ID = "ID";
    public static final String JWT_ISSUER = "RECIPEGPT";
    public static final Long ACCESS_TOKEN_TIME_TO_LIVE = 2 * 60 * 60 * 1000L;
    public static final Long REFRESH_TOKEN_TIME_TO_LIVE = 7 * 24 * 60 * 60 * 1000L;
    public static final long REFRESH_TOKEN_TIME_TO_LIVE_PRIMITIVE = 7 * 24 * 60 * 60 * 1000L;
    public static final String JWT_EXCEPTION = "exception";
    public static final String TOKEN_KEY = "token";

}

