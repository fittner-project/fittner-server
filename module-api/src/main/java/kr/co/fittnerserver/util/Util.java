package kr.co.fittnerserver.util;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.StringJoiner;

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

    /**
     * HttpServletRquest에서 파라미터를 가져오는 유틸 메서드
     *
     * @param request HttpServletRequest 객체
     * @return String
     */
    public static String getClientParams(HttpServletRequest request) throws IOException {

        String result = "";

        String requestMethod = request.getMethod();

        // GET 방식에서 파라미터 처리
        if ("GET".equalsIgnoreCase(requestMethod)) {
            // URL 쿼리 파라미터
            result = Util.getQueryParams(request);

        } else if ("POST".equalsIgnoreCase(requestMethod)) {
            // POST 방식에서 body 파라미터 처리
            String contentType = request.getContentType();

            // application/x-www-form-urlencoded 처리
            if (contentType != null && contentType.startsWith("application/x-www-form-urlencoded")) {
                result = Util.getFormUrlEncodedParams(request);
            }
            // application/json 처리
            else if (contentType != null && contentType.startsWith("application/json")) {
                result = Util.getJsonBody(request);
            }
        }

        return result;
    }

    /**
     * GET 방식에서 쿼리 파라미터를 가져오는 유틸 메서드
     *
     * @param request HttpServletRequest 객체
     * @return 쿼리 파라미터
     */
    public static String getQueryParams(HttpServletRequest request) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringJoiner queryParams = new StringJoiner("&");

        parameterMap.forEach((key, value) -> queryParams.add(key + "=" + String.join(",", value)));

        return queryParams.toString();
    }

    /**
     * application/x-www-form-urlencoded 방식의 파라미터를 가져오는 유틸 메서드
     *
     * @param request HttpServletRequest 객체
     * @return 폼 파라미터
     */
    public static String getFormUrlEncodedParams(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringJoiner formParams = new StringJoiner("&");

        parameterMap.forEach((key, value) -> formParams.add(key + "=" + String.join(",", value)));

        return formParams.toString();
    }

    /**
     * application/json 방식의 JSON 데이터를 가져오는 유틸 메서드
     *
     * @param request HttpServletRequest 객체
     * @return JSON 문자열
     * @throws IOException IOException
     */
    public static String getJsonBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString(); // JSON 본문 반환
    }
}
