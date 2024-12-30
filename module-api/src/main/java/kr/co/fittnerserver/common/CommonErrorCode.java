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
    AUTHENTICATION_FAILED("H006","인증에 실패하였습니다.");

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
