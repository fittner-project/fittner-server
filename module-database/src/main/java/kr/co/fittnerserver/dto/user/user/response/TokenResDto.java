package kr.co.fittnerserver.dto.user.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResDto {
    @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0cmFpbmVyQG5hdmVyLmNvbSIsImV4cCI6MTYzNzQwNjQwNn0.7")
    private String accessToken;
    @Schema(description = "리프레시 토큰",example = "alskdnksla")
    private String refreshTokenId;
}
