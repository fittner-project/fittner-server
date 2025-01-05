package kr.co.fittnerserver.dto.user.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AccessTokenReqDto {
    @Schema(description = "만료된 토큰값", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmNkZWYiLCJleHAiOjE3MzU1NDI0OTh9.KU9cRantYrLEFmMyExdedwpuz10k76JeaYDR1D2bPGI")
    private String accessToken;
    @Schema(description = "리프레시 토큰 키값", example = "alskdnksla")
    private String refreshTokenId;
}
