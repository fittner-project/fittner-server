package kr.co.fittnerserver.dto.user.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TermsResDto {

    @Schema(description = "약관ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    private String termsId;

    @Schema(description = "약관종류", example = "ADVERTISE | INFO | USE")
    private String termsKind;

    @Schema(description = "약관필수여부", example = "Y")
    private String termsEssentialYn;

    @Schema(description = "약관제목", example = "개인정보처리방침")
    private String termsTitle;

    @Schema(description = "약관url", example = "https://api.fittner.co.kr/api/v1/common/file/show/bE2c2E35d6a")
    private String termsUrl;
}
