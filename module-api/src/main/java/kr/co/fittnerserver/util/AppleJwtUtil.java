package kr.co.fittnerserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class AppleJwtUtil {

    @Value("${apple.key}")
    private String privateKey;
    @Value("${apple.team-id}")
    private String teamId;
    @Value("${apple.client-id}")
    private String clientId;
    @Value("${apple.key-id}")
    private String keyId;

    private final String appleTokenUrl = "https://appleid.apple.com/auth/token";

    public String generateClientSecret() throws Exception {
        // Private Key 생성
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        PrivateKey signingKey = KeyFactory.getInstance("EC").generatePrivate(keySpec);

        long now = System.currentTimeMillis() / 1000;  // 초 단위로 변환

        // JWT 생성
        return Jwts.builder()
                .setHeaderParam("kid", keyId)
                .setHeaderParam("alg", "ES256")
                .setIssuer(teamId)
                .setAudience("https://appleid.apple.com")
                .setSubject(clientId)
                .setIssuedAt(new Date(now * 1000))
                .setExpiration(new Date((now + 3600) * 1000))
                .signWith(signingKey, SignatureAlgorithm.ES256)
                .compact();
    }

    public Map<String, Object> requestAppleToken(String authorizationCode, String clientSecret) throws Exception {
        // HTTP 요청 생성
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(appleTokenUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code" +
                                "&code=" + authorizationCode +
                                "&client_id=" + clientId +
                                "&client_secret=" + clientSecret))
                .build();

        // HTTP 응답
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 응답 JSON 파싱
        return new ObjectMapper().readValue(response.body(), Map.class);
    }

    public Map<String, Object> decodeIdToken(String idToken) throws JsonProcessingException {
        // ID Token 디코딩 및 Claims 추출
        String[] parts = idToken.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        return new ObjectMapper().readValue(payload, Map.class);
    }
}
