package kr.co.fittnerserver.results;

public enum FittnerErrorCode {
    // common
    COMMON_FAIL("E000", "실패");
    private final String code;
    private final String message;

    FittnerErrorCode(final String code, final String message) {
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
