package kr.co.fittnerserver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret-key}")
    private String secretKey;

    // 액세스 토큰 생성
    public String generateAccessToken(String trainerId) {
        return JWT.create()
                .withSubject(trainerId)
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000)) //24시간
                .sign(Algorithm.HMAC256(secretKey));
    }

    // 리프레시 토큰 생성
    public String generateRefreshToken(String trainerId) {
        return JWT.create()
                .withSubject(trainerId)
                .withExpiresAt(new Date(System.currentTimeMillis() + 604800000)) //1주일
                .sign(Algorithm.HMAC256(secretKey));
    }

    // 토큰 검증 및 사용자 이름 반환
    public String validateTokenAndGetTrainerId(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}
