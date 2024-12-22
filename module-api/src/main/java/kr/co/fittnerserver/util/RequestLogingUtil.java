package kr.co.fittnerserver.util;

import java.io.IOException;
import kr.co.fittnerserver.wrapper.CustomHttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

public class RequestLogingUtil {

    /**
     * 클라이언트의 IP를 추출
     *
     * @param request HiBallHttpServletRequestWrapper 객체
     * @return 클라이언트의 IP 주소
     */
    public static String getClientIp(CustomHttpServletRequestWrapper request) {
        // 순서대로 검사할 헤더 리스트
        final String[] HEADERS_TO_TRY = {
                "X-Forwarded-For",
                "X-Real-IP"
        };

        // 헤더에서 IP 확인
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                // X-Forwarded-For 헤더에 다중 IP가 있는 경우 첫 번째 IP 반환
                return ip.split(",")[0].trim();
            }
        }

        // 헤더에서 찾을 수 없으면 기본적으로 getRemoteAddr() 사용
        return request.getRemoteAddr();
    }

    /**
     * HttpServletRequest에서 파라미터 get
     *
     * @param request HiBallHttpServletRequestWrapper 객체
     * @return 요청 파라미터 문자열
     * @throws IOException IOException
     */
    public static String getClientParams(CustomHttpServletRequestWrapper request) throws IOException {
        String requestMethod = request.getMethod();
        String contentType = request.getContentType();

        // GET 방식 쿼리 파라미터 처리
        if ("GET".equalsIgnoreCase(requestMethod)) {
            return getQueryParams(request);
        }

        // POST 방식 처리
        if ("POST".equalsIgnoreCase(requestMethod) && contentType != null) {
            if (contentType.startsWith("application/x-www-form-urlencoded")) {
                return getFormUrlEncodedParams(request);
            } else if (contentType.startsWith("application/json")) {
                return getJsonBody(request);
            }
        }

        // 지원하지 않는 요청 방식
        return "";
    }

    /**
     * GET 방식에서 쿼리 파라미터 get
     *
     * @param request HiBallHttpServletRequestWrapper 객체
     * @return 쿼리 파라미터 문자열
     */
    public static String getQueryParams(CustomHttpServletRequestWrapper request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringJoiner queryParams = new StringJoiner("&");

        parameterMap.forEach((key, value) ->
                queryParams.add(key + "=" + String.join(",", value))
        );

        return queryParams.toString();
    }

    /**
     * application/x-www-form-urlencoded 방식의 파라미터 get
     *
     * @param request HiBallHttpServletRequestWrapper 객체
     * @return 폼 파라미터 문자열
     */
    public static String getFormUrlEncodedParams(CustomHttpServletRequestWrapper request) throws IOException {
        StringBuilder formData = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        String line;

        while ((line = reader.readLine()) != null) {
            formData.append(line);
        }

        String formString = formData.toString();
        StringJoiner formParams = new StringJoiner("&");

        // URLDecode the parameters and add to the result
        if (!formString.isEmpty()) {
            String[] params = formString.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    formParams.add(URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8) + "=" + URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8));
                }
            }
        }

        return formParams.toString();
    }

    /**
     * application/json 방식의 JSON 데이터를 get
     *
     * @param request HiBallHttpServletRequestWrapper 객체
     * @return JSON 문자열
     * @throws IOException IOException
     */
    public static String getJsonBody(CustomHttpServletRequestWrapper request) throws IOException {
        String body = request.getBody();
        return body != null ? body.replaceAll("[\\n\\r]", "") : "";
    }

    /**
     * 클라이언트의 content-type을 추출
     *
     * @param request HiBallHttpServletRequestWrapper 객체
     * @return String
     */
    public static String getContentType(CustomHttpServletRequestWrapper request) {
        return request.getContentType() == null ? "query-string" : request.getContentType();
    }
}
