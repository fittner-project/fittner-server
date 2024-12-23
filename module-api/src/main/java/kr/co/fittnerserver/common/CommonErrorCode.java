package kr.co.fittnerserver.common;


public enum CommonErrorCode {
    // common
    SUCCESS("SUCCESS", "정상처리"),
    FAIL("FAIL", "실패"),
    COMMON_FAIL("A000", "실패"),
    NOT_FOUND_TRAINER("H001","트레이너를 찾을 수 없습니다.");


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
