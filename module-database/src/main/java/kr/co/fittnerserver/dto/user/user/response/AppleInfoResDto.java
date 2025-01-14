package kr.co.fittnerserver.dto.user.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppleInfoResDto {
    @Schema(description = "애플 로그인 시 필요한 유저이메일", example = "newy12@naver.com")
    private String userEmail;
}
