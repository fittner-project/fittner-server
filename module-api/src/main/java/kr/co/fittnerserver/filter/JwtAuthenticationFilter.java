package kr.co.fittnerserver.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.fittnerserver.auth.CustomUserDetailsService;
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
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String trainerId = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            //블랙리스트 토큰 확인
            if(blackListTokenRepository.existsByAccessToken(token)){
                throw new RuntimeException("블랙리스트에 들어있는 토큰입니다.");
            }
            trainerId = jwtTokenUtil.validateTokenAndGetTrainerId(token);
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
