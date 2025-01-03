package kr.co.fittnerserver.common;


public enum CommonErrorCode {
    // common
    SUCCESS("SUCCESS", "정상처리"),
    FAIL("FAIL", "실패"),
    COMMON_FAIL("A000", "실패"),
    NOT_FOUND_TRAINER("H001","트레이너를 찾을 수 없습니다."),
    EXIST_BLACKLIST_TOKEN("H002","블랙리스트 토큰이 존재합니다."),
    WRONG_TOKEN("H003","토큰이 유효하지 않거나 잘못되었습니다."),
    INVALID_TOKEN("H004","토큰이 유효하지 않거나 잘못되었습니다."),
    EXPIRED_TOKEN("H005","토큰이 만료되었습니다"),
    AUTHENTICATION_FAILED("H006","인증에 실패하였습니다."),
    NOT_FOUND_AUTH_HEADER("H007","헤더에 인증정보가 없습니다."),
    LOGOUT_FAIL("H008","로그아웃에 실패하였습니다."),
    NOT_NEED_AUTH_HEADER("H009","인증정보가 필요 없는 요청입니다."),
    NOT_FOUND_REFRESH_TOKEN_INFO("H010","리프레시 토큰 정보를 찾을 수 없습니다."),
    GOOD_TOKEN("H011","유효한 토큰입니다."),
    ALREADY_TRAINER("H012","이미 가입된 트레이너입니다."),
    NOT_FOUND_CENTER("H013","센터를 찾을 수 없습니다.");

    private final String code;
    private final String message;

    CommonErrorCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
