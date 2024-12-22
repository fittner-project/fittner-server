package kr.co.fittnerserver.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingMDCFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // UUID로 request_id 생성
        String requestId = UUID.randomUUID().toString();

        // MDC에 request_id 추가
        MDC.put("request_id", requestId);

        try {
            // 요청 처리
            filterChain.doFilter(request, response);
        } finally {
            // 요청 처리 후 MDC 제거
            MDC.clear();
        }
    }

}
