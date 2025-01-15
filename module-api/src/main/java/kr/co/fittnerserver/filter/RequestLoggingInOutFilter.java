package kr.co.fittnerserver.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.fittnerserver.util.RequestLogingUtil;
import kr.co.fittnerserver.wrapper.CustomHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@Slf4j
public class RequestLoggingInOutFilter extends OncePerRequestFilter {

    private String REQUEST_INFO_PRI_FIX = "-----------------------[REQUEST-INFO]-----------------------";
    private String RESPONSE_INFO_PRI_FIX = "-----------------------[RESPONSE-INFO]-----------------------";
    private String REQUEST_PRI_FIX = "--->";
    private String RESPONSE_PRI_FIX = "<---";
    private String[] NOT_ALLOW_LOG = {"/swagger-ui","/v3/api-docs","/api/v1/common/file/upload","/api/v1/user/sign/upload","/api/v1/auth/apple/test"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //log출력 여부
        boolean logChkeck = Arrays.stream(NOT_ALLOW_LOG)
                .anyMatch(item -> request.getRequestURI().contains(item));

        if(!logChkeck){

            //requset(custom), response(caching)
            CustomHttpServletRequestWrapper wrappedRequest = new CustomHttpServletRequestWrapper(request); //요청을 CustomHttpServletRequestWrapper로 감싸기
            ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

            //request log
            beforeRequest(wrappedRequest);

            // 필터 체인 실행
            filterChain.doFilter(wrappedRequest, wrappedResponse);

            //response log
            afterRequest(wrappedResponse);

        }else {
            filterChain.doFilter(request, response);
        }
    }

    protected void beforeRequest(CustomHttpServletRequestWrapper wrapperRequest) throws IOException {
        log.info(REQUEST_INFO_PRI_FIX);
        log.info("{} client ip ::: {}", REQUEST_PRI_FIX, RequestLogingUtil.getClientIp(wrapperRequest));
        log.info("{} url ::: {}", REQUEST_PRI_FIX, wrapperRequest.getRequestURI());
        log.info("{} method ::: {}", REQUEST_PRI_FIX, wrapperRequest.getMethod());
        log.info("{} content-type ::: {}", REQUEST_PRI_FIX, RequestLogingUtil.getContentType(wrapperRequest));
        log.info("{} content ::: {}", REQUEST_PRI_FIX, RequestLogingUtil.getClientParams(wrapperRequest));
    }

    protected void afterRequest(ContentCachingResponseWrapper wrappedResponse) throws IOException{

        byte[] content = wrappedResponse.getContentAsByteArray();
        String contentStr = new String(content, StandardCharsets.UTF_8);

        if (wrappedResponse.getContentType() != null) {
            // 응답 log 출력 제외 (html, image)
            if (wrappedResponse.getContentType().startsWith("text/html")) {
                contentStr = "html result";
            }
            if (wrappedResponse.getContentType().startsWith("image/")) {
                contentStr = "image result";
            }
        }

        log.info(RESPONSE_INFO_PRI_FIX);
        log.info("{} http status ::: {}", RESPONSE_PRI_FIX, wrappedResponse.getStatus());
        log.info("{} result ::: {}", RESPONSE_PRI_FIX, contentStr);

        // 응답 본문을 원본 응답으로 복사
        wrappedResponse.copyBodyToResponse();
    }


}
