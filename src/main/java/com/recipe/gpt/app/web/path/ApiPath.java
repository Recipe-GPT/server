package com.recipe.gpt.app.web.path;

public class ApiPath {

    // 인증
    public static final String LOGIN_OAUTH2 = "/api/v1/oauth";
    public static final String REFRESH_TOKEN = "/api/v1/oauth/refresh";
    public static final String VALIDATE_TOKEN = "/api/v1/oauth/validate";
    public static final String LOGOUT = "/api/v1/logout";

    // 멤버
    public static final String MEMBER = "/api/v1/member";

    // 레시피 질문
    public static final String QUERY_RECOMMEND = "/api/v1/query/recommend/{chatRoomId}";
    public static final String QUERY_RECIPE = "/api/v1/query/recipe/{chatRoomId}";

    // 식재료, 양념 검색
    public static final String SEARCH_INGREDIENT = "/api/v1/search/ingredient";
    public static final String SEARCH_SEASONING = "/api/v1/search/seasoning";

    // 채팅방
    public static final String CHATROOM_CREATE = "/api/v1/chatroom";
    public static final String CHATROOM_DELETE = "/api/v1/chatroom/delete/{id}";
    public static final String CHATROOM_FIND = "/api/v1/chatroom/find";
    public static final String CHATROOM_UPDATE = "/api/v1/chatroom/update/{id}";
    public static final String CHATROOM_CHAT_LIST = "/api/v1/chatroom/find/{id}";

    // 커뮤니티
    public static final String BOARD_UPLOAD = "/api/v1/board";
    public static final String BOARD_UPDATE = "/api/v1/board/update/{id}";
    public static final String BOARD_DELETE = "/api/v1/board/delete/{id}";
    public static final String BOARD_VIEW_DETAIL = "/api/v1/board/detail/{id}";
    public static final String BOARD_VIEW_RECOMMEND = "/api/v1/board/recommend";
    public static final String BOARD_VIEW_FILTER = "/api/v1/board/filter";
    public static final String BOARD_VIEW_TRENDING = "/api/v1/board/trending";

    // 에러 핸들러
    public static final String ERROR = "/api/v1/error";
}