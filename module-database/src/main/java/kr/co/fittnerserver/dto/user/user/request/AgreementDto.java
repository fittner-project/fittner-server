package kr.co.fittnerserver.dto.user.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AgreementDto {
    @Schema(description = "약관 ID", example = "0f83adb5-d27a-11ef-b7c9-0242ac190002")
    private String termsId;

    @Schema(description = "약관 동의 여부", example = "Y")
    private String agreed;
}
