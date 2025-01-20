package kr.co.fittnerserver.dto.user.sign.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignResrvationForMemberResDto {

    @Schema(description = "예약ID", example = "3c7ba936-d4d3-11ef-b7c9-0242ac190002")
    private String reservationId;

    @Schema(description = "회원ID", example = "3c7ba936-d4d3-11ef-b7c9-0242ac190002")
    private String memberId;

    @Schema(description = "회원이름", example = "아무개")
    private String memberName;

    @Schema(description = "예약상태", example = "NOSHOW | SIGN | WATING")
    private String reservationStatus;

    @Schema(description = "예약시작일", example = "20250104")
    private String reservationStartDate;

    @Schema(description = "예약시작시간", example = "1400")
    private String reservationStartTime;

    @Schema(description = "예약종료시간", example = "1500")
    private String reservationEndTime;

    @Schema(description = "예약회차", example = "4")
    private String reservationUseCnt;

}
