package kr.co.fittnerserver.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class RequestLoggingInOutFilter extends OncePerRequestFilter {

    private String REQUEST_INFO_PRI_FIX = "-----------------------[REQUEST-INFO]-----------------------";
    private String RESPONSE_INFO_PRI_FIX = "-----------------------[RESPONSE-INFO]-----------------------";
    private String REQUEST_PRI_FIX = "--->";
    private String RESPONSE_PRI_FIX = "<---";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //copy
        ContentCachingResponseWrapper wrapperResponse = wrapperRespnse(response);
        ContentCachingRequestWrapper wrapperRequest = wrapperRequest(request);

        //request log
        beforeRequest(wrapperRequest);
        //return
        filterChain.doFilter(wrapperRequest,wrapperResponse);
        //response log
        afterRequest(wrapperResponse);

        //client response copy
        wrapperResponse.copyBodyToResponse();
    }

    protected void beforeRequest(ContentCachingRequestWrapper wrapperRequest) throws IOException{
        wrapperRequest.getParameterNames();
        byte[] content = wrapperRequest.getContentAsByteArray();
        String contentStr = URLDecoder.decode(new String(content, wrapperRequest.getCharacterEncoding()),String.valueOf(StandardCharsets.UTF_8));

        log.info(REQUEST_INFO_PRI_FIX);
        log.info("{} client ip ::: {}", RESPONSE_PRI_FIX, "----");
        log.info("{} url ::: {}", REQUEST_PRI_FIX, wrapperRequest.getRequestURI());
        log.info("{} method ::: {}", REQUEST_PRI_FIX, wrapperRequest.getMethod());
        log.info("{} content-type ::: {}", REQUEST_PRI_FIX, wrapperRequest.getContentType());
        log.info("{} content ::: {}", REQUEST_PRI_FIX, contentStr);
    }

    protected void afterRequest(ContentCachingResponseWrapper wrapperResponse) throws IOException{
        byte[] content = wrapperResponse.getContentAsByteArray();
        String contentStr = new String(content, wrapperResponse.getCharacterEncoding());
        if(wrapperResponse.getContentType() != null){
            //log 출력제외
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
