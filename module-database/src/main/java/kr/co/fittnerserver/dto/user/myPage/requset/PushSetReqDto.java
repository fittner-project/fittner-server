package kr.co.fittnerserver.dto.user.myPage.requset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PushSetReqDto {

    @Schema(description = "푸시종류", example = "ADVERTISE | RESERVATION")
    @NotBlank
    private String pushKind;

    @Schema(description = "푸시설정여부", example = "Y")
    @NotBlank
    private String pushSetYn;
}
