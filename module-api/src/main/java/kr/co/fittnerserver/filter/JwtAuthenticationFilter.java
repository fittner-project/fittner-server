package kr.co.fittnerserver.filter;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.fittnerserver.auth.CustomUserDetailsService;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.exception.JwtException;
import kr.co.fittnerserver.repository.BlackListTokenRepository;
import kr.co.fittnerserver.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;
    private final BlackListTokenRepository blackListTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String trainerId = null;

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                //블랙리스트 토큰 확인
                if(blackListTokenRepository.existsByAccessToken(token)){
                    throw new JwtException(CommonErrorCode.EXIST_BLACKLIST_TOKEN.getCode(), CommonErrorCode.EXIST_BLACKLIST_TOKEN.getMessage());
                }
                trainerId = jwtTokenUtil.validateTokenAndGetTrainerId(token);
                if(trainerId == null) {
                    throw new JwtException(CommonErrorCode.INVALID_TOKEN.getCode(), CommonErrorCode.INVALID_TOKEN.getMessage());
                }
            }
        } catch (MalformedJwtException e) {
            throw new JwtException(CommonErrorCode.WRONG_TOKEN.getCode(), CommonErrorCode.WRONG_TOKEN.getMessage(), e);
        } catch(IllegalArgumentException e) {
            throw new JwtException(CommonErrorCode.INVALID_TOKEN.getCode(), CommonErrorCode.INVALID_TOKEN.getMessage());
        } catch(ExpiredJwtException e) {
            throw new JwtException(CommonErrorCode.EXPIRED_TOKEN.getCode(), CommonErrorCode.EXPIRED_TOKEN.getMessage());
        } catch(SignatureException e) {
            throw new JwtException(CommonErrorCode.AUTHENTICATION_FAILED.getCode(), CommonErrorCode.AUTHENTICATION_FAILED.getMessage(), e);
        }

        if (trainerId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(trainerId);

            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
