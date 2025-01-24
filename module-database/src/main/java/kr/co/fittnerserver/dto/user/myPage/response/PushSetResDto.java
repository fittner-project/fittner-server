package kr.co.fittnerserver.dto.user.myPage.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PushSetResDto {

    @Schema(description = "푸시종류", example = "ADVERTISE | RESERVATION")
    private String pushKind;

    @Schema(description = "푸시여부", example = "Y")
    private String pushSetYn;
}
