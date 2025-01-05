package kr.co.fittnerserver.util;

public class PhoneFormatUtil {
    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || (phoneNumber.length() != 10 && phoneNumber.length() != 11)) {
            throw new IllegalArgumentException("유효하지 않은 전화번호입니다.");
        }

        // 10자리인 경우 "010-3330-033"처럼 포맷
        if (phoneNumber.length() == 10) {
            return phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6);
        }

        // 11자리인 경우 "010-5721-2058"처럼 포맷
        return phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 7) + "-" + phoneNumber.substring(7);
    }
}
