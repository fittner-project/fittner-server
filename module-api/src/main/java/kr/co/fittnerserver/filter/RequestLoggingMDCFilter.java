package kr.co.fittnerserver.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class RequestLoggingMDCFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // UUID로 request_id 생성
        String requestId = UUID.randomUUID().toString();

        // MDC에 request_id 추가
        MDC.put("request_id", requestId);

        try {
            // 요청 처리
            filterChain.doFilter(request, response);
        } finally {
            // 요청 처리 후 MDC 제거
            MDC.remove("request_id");
        }
    }

}
