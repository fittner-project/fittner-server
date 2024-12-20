package kr.co.fittnerserver.util;

import jakarta.servlet.http.HttpServletRequest;

public class Util {

    /**
     * 클라이언트의 IP를 추출하는 메서드
     *
     * @param request HttpServletRequest 객체
     * @return 클라이언트의 IP 주소
     */
    public static String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");

        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getHeader("X-Real-IP");
        }

        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }

        // X-Forwarded-For 헤더에는 여러 IP가 쉼표로 구분되어 있을 수 있음
        if (clientIp != null && clientIp.contains(",")) {
            clientIp = clientIp.split(",")[0]; // 첫 번째 IP가 원래의 클라이언트 IP
        }

        return clientIp;
    }
}
