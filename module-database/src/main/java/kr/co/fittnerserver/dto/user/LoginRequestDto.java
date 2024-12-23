package kr.co.fittnerserver.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequestDto {
    @Schema(description = "이메일", example = "newy12@naver.com")
    private String trainerEmail;
}
