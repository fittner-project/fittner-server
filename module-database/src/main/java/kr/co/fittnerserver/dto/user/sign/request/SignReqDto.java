package kr.co.fittnerserver.dto.user.sign.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignReqDto {

    @Schema(description = "예약ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    @NotBlank
    private String reservationId;

    @Schema(description = "회원ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    @NotBlank
    private String memberId;

    @Schema(description = "파일그룹ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    private String fileGroupId;

    @Schema(description = "서명타입", example = "SIGN | NOSHOW")
    @Pattern(regexp = "SIGN|NOSHOW")
    @NotBlank
    private String signType;

    @Schema(description = "서명메모", example = "대화내용 캡쳐본 첨부")
    private String signMemo;

}
