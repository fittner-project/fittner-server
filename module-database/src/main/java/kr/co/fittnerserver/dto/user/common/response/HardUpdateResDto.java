package kr.co.fittnerserver.dto.user.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class HardUpdateResDto {

    @Schema(description = "강제업데이트여부", example = "Y")
    private String hardUpdateYn;

    @Schema(description = "업데이트url", example = "스토어url")
    private String appUpadateUrl;
}
