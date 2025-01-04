package kr.co.fittnerserver.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.fittnerserver.auth.CustomUserDetailsService;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.exception.JwtException;
import kr.co.fittnerserver.repository.BlackListTokenRepository;
import kr.co.fittnerserver.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;
    private final BlackListTokenRepository blackListTokenRepository;
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final List<String> EXCLUDED_URLS = Arrays.asList(
            "/api/v1/auth/login",
            "/api/v1/auth/refresh-token",
            "/actuator/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api/v1/user/join",
            "/api/v1/center/list",
            "/api/v1/user/app/version-chk"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String trainerId = null;

        String requestURI = request.getRequestURI();
        boolean isExcluded = EXCLUDED_URLS.stream().anyMatch(pattern -> pathMatcher.match(pattern, requestURI));

        if (authorizationHeader == null && !isExcluded) {
            throw new JwtException(CommonErrorCode.NOT_FOUND_AUTH_HEADER.getCode(), CommonErrorCode.NOT_FOUND_AUTH_HEADER.getMessage());
        }


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            if (isExcluded) {
                throw new JwtException(CommonErrorCode.NOT_NEED_AUTH_HEADER.getCode(), CommonErrorCode.NOT_NEED_AUTH_HEADER.getMessage());
            }
            token = authorizationHeader.substring(7);
            //블랙리스트 토큰 확인
            if (blackListTokenRepository.existsByAccessToken(token)) {
                throw new JwtException(CommonErrorCode.EXIST_BLACKLIST_TOKEN.getCode(), CommonErrorCode.EXIST_BLACKLIST_TOKEN.getMessage());
            }
            trainerId = jwtTokenUtil.validateTokenAndGetTrainerId(token);
            if (trainerId == null) {
                throw new JwtException(CommonErrorCode.INVALID_TOKEN.getCode(), CommonErrorCode.INVALID_TOKEN.getMessage());
            }
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
