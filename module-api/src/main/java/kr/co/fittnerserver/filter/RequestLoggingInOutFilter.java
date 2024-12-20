package kr.co.fittnerserver.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.fittnerserver.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

import java.util.Arrays;

@Component
@Slf4j
public class RequestLoggingInOutFilter extends OncePerRequestFilter {

    private String REQUEST_INFO_PRI_FIX = "-----------------------[REQUEST-INFO]-----------------------";
    private String RESPONSE_INFO_PRI_FIX = "-----------------------[RESPONSE-INFO]-----------------------";
    private String REQUEST_PRI_FIX = "--->";
    private String RESPONSE_PRI_FIX = "<---";
    private String[] NOT_ALLOW_LOG = {"/swagger-ui","/v3/api-docs"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //copy
        ContentCachingResponseWrapper wrapperResponse = wrapperRespnse(response);
        ContentCachingRequestWrapper wrapperRequest = wrapperRequest(request);

        //log출력 여부
        boolean logChkeck = Arrays.stream(NOT_ALLOW_LOG)
                .anyMatch(item -> wrapperRequest.getRequestURI().contains(item));

        //request log
        beforeRequest(wrapperRequest, !logChkeck);
        //return
        filterChain.doFilter(wrapperRequest,wrapperResponse);
        //response log
        afterRequest(wrapperResponse, !logChkeck);

        //client response copy
        wrapperResponse.copyBodyToResponse();
    }

    protected void beforeRequest(ContentCachingRequestWrapper wrapperRequest, boolean logChkeck) throws IOException{

        if(logChkeck){
            log.info(REQUEST_INFO_PRI_FIX);
            log.info("{} client ip ::: {}", REQUEST_PRI_FIX, Util.getClientIp(wrapperRequest));
            log.info("{} url ::: {}", REQUEST_PRI_FIX, wrapperRequest.getRequestURI());
            log.info("{} method ::: {}", REQUEST_PRI_FIX, wrapperRequest.getMethod());
            log.info("{} content-type ::: {}", REQUEST_PRI_FIX, wrapperRequest.getContentType());
            log.info("{} content ::: {}", REQUEST_PRI_FIX, Util.getClientParams(wrapperRequest));
        }
    }

    protected void afterRequest(ContentCachingResponseWrapper wrapperResponse, boolean logChkeck) throws IOException{

        if(logChkeck){
            byte[] content = wrapperResponse.getContentAsByteArray();
            String contentStr = new String(content, wrapperResponse.getCharacterEncoding());
            if(wrapperResponse.getContentType() != null){
                //응답 log 출력제외(html,image)
                if(wrapperResponse.getContentType().startsWith("text/html")){
                    contentStr = "html result";
                }
                if(wrapperResponse.getContentType().startsWith("image/")){
                    contentStr = "image result";
                }
            }
            log.info(RESPONSE_INFO_PRI_FIX);
            log.info("{} http status ::: {}",RESPONSE_PRI_FIX, wrapperResponse.getStatus());
            log.info("{} result ::: {}", RESPONSE_PRI_FIX, contentStr);
        }
    }

    private ContentCachingRequestWrapper wrapperRequest(HttpServletRequest request){
        if(request instanceof  ContentCachingRequestWrapper){
            return (ContentCachingRequestWrapper) request;
        }
        return new ContentCachingRequestWrapper(request);
    }

    private ContentCachingResponseWrapper wrapperRespnse(HttpServletResponse response){
        if(response instanceof  ContentCachingResponseWrapper){
            return (ContentCachingResponseWrapper) response;
        }
        return new ContentCachingResponseWrapper(response);
    }

}
