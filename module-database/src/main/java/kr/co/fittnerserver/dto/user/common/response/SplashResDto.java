package kr.co.fittnerserver.dto.user.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SplashResDto {

    @Schema(description = "스플래시 이미지 url", example = "https://api.fittner.co.kr/api/v1/common/file/show/1Ec3Eb2h1B9")
    private String splashImgUrl;
}
