package kr.co.fittnerserver.dto.user.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AppleInfoReqDto {
    @Schema(description = "애플 로그인 시 필요한 인증코드", example = "201205211618573161SP")
    private String user;
}
