package kr.co.fittnerserver.dto.user.sign.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignResrvationDto {

    @Schema(description = "이용권ID", example = "3c7ba936-d4d3-11ef-b7c9-0242ac190002")
    private String ticketId;

    @Schema(description = "예약ID", example = "3c7ba936-d4d3-11ef-b7c9-0242ac190002")
    private String reservationId;

    @Schema(description = "회원이름", example = "홍길동")
    private String memberName;

    @Schema(description = "예약회차", example = "3")
    private String reservationUseCnt;

    @Schema(description = "예약시작시간", example = "1400")
    private String reservationStartTime;

    @Schema(description = "예약종료시간", example = "1500")
    private String reservationEndTime;

    @Schema(description = "예약메모", example = "야외수업 희망")
    private String reservationMemo;

    @Schema(description = "예약상태", example = "NOSHOW | SIGN | WATING")
    private String reservationStatus;

    @Schema(description = "예약색상", example = "COLOR_007AFF")
    private String reservationColor;

}
