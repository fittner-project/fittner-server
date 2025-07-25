package kr.co.fittnerserver.common;


public enum CommonErrorCode {
    // common
    SUCCESS("SUCCESS", "정상처리"),
    SUCCESS_CODE("0000", "정상처리"),
    FAIL("FAIL", "실패"),
    FAIL_CODE("9999", "실패"),
    //EXCEPTION("9999","관리자에게 문의해주세요."),
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
    NOT_FOUND_CENTER("H013","센터를 찾을 수 없습니다."),
    NOT_ALLOW_FILE("H013","허용되지 않은 확장자입니다."),
    NOT_FOUND_FILE_GROUP("H014","파일 그룹을 찾을 수 없습니다."),
    NOT_FILE("H015","파일이 없습니다."),
    NOT_FOUND_MEMBER("H016","회원을 찾을 수 없습니다."),
    NOT_FOUND_TICKET("H017","티켓을 찾을 수 없습니다."),
    NOT_FOUND_CENTER_JOIN("H018","센터 가입 정보를 찾을 수 없습니다."),
    NOT_MATCH_TRAINER("H019","트레이너 정보가 일치하지 않습니다."),
    APPLE_FAIL("H020","애플 통신 중 에러가 발생하였습니다."),
    NOT_FOUND_TRAINER_PRODUCT("H021","트레이너 상품을 찾을 수 없습니다."),
    NOT_ASSIGN_SELF("H022","본인에게는 양도가 불가능합니다."),
    ERR_TICKET_START_END_DATE("H023","티켓 이용일이 잘못되었습니다."),
    NOT_FOUND_TERMS("H024","약관을 찾을 수 없습니다."),
    NOT_FOUND_TERMS_AGREE("H025","약관 동의 정보를 찾을 수 없습니다."),
    RESERVATION_TICKET("H024","수업 예정인 이용권입니다."),
    DROP_FAIL("H025","트레이너 계정 탈퇴에 실패하였습니다."),
    EXIST_DROP_TRAINER("H026","탈퇴한 트레이너입니다."),
    NOT_FOUND_RESERVATION("H027","수업 정보를 찾을 수 없습니다."),
    ALREADY_SIGN("H028","이미 처리된 예약입니다."),
    NOT_FOUND_SETTLEMENT("H029","정산 정책을 찾을 수 없습니다."),
    NOT_ACTIVE_TRAINER("H030","승인 대기중인 트레이너입니다."),
    NOT_ALLOW_TRAINER_MEMBER("H031","트레이너에 소속된 회원이 아닙니다."),
    NOT_REFUND("H032","환불이 불가능한 이용권입니다."),
    NOT_ASSIGN("H033","양도가 불가능한 이용권입니다."),
    ALREADY_SUSPEND_TICKET("H034","이미 정지된 이용권입니다."),
    ALREADY_ING_TICKET("H035","이미 사용중인 이용권입니다."),
    PUSH_ERROR("H036","푸시 전송 중 에러가 발생하였습니다."),
    NOT_REQUEST_REFUND("H037","남아있는 예약이 있어 환불을 진행할수가없습니다."),
    DATE_ERROR("H038","시작일자와 종료일자를 확인해주세요."),
    PRICE_ERROR("H039","상품횟수 및 상품금액을 확인해주세요."),
    SIGN_DATE_ERROR("H040","수업 시작전 서명이 불가합니다."),
    NOT_ING_TICKET("H041","수업예약일에 이용중인 이용권 기간이 없습니다.")

    ;


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
