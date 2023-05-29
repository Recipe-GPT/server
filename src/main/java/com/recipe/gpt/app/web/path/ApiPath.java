package com.recipe.gpt.app.web.path;

public class ApiPath {

    // 인증
    public static final String LOGIN_OAUTH2 = "/api/v1/oauth";

    public static final String REFRESH_TOKEN = "/api/v1/oauth/refresh";

    public static final String VALIDATE_TOKEN = "/api/v1/oauth/validate";

    // 멤버
    public static final String MEMBER = "/api/v1/member";

    // 레시피 질문
    public static final String RECIPE_QUERY = "/api/v1/recipe/query";

    public static final String SEARCH_INGREDIENT = "/api/v1/search/ingredient";

    // 에러 핸들러
    public static final String ERROR_AUTH = "/api/v1/error";


}