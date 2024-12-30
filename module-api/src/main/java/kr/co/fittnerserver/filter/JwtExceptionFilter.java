package kr.co.fittnerserver.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.fittnerserver.common.ApiResponseMessage;
import kr.co.fittnerserver.exception.JwtException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (JwtException ex){
            setJwtErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
        }
    }

    public void setJwtErrorResponse(HttpStatus status, HttpServletResponse res, JwtException ex) throws IOException {
        res.setStatus(status.value());
        res.setContentType("application/json; charset=UTF-8");

        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setStatus("FAIL");
        apiResponseMessage.setMessage("실패");
        apiResponseMessage.setErrorCode(ex.getCode());
        apiResponseMessage.setErrorMessage(ex.getMessage());
        JSONObject responseJson = new JSONObject(apiResponseMessage);

        res.getWriter().print(responseJson);
    }
}
