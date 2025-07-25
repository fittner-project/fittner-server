package kr.co.fittnerserver.dto.user.reservation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReservationReqDto {
    @Schema(description = "회원 키값", example = "1")
    private String memberId;

    //@Schema(description = "티켓 키값", example = "1")
    //private String ticketId;

    @Schema(description = "예약 시작일자", example = "20250105")
    private String reservationStartDate;

    @Schema(description = "예약 종료일자", example = "20250106")
    private String reservationEndDate;

    @Schema(description = "예약 시작시간", example = "2030")
    private String reservationStartTime;

    @Schema(description = "예약 종료일자", example = "2130")
    private String reservationEndTime;

    @Schema(description = "색상 태그", example = "007AFF")
    private String reservationColor;

    @Schema(description = "예약 푸시", example = "before_5m")
    private String reservationPush;

    @Schema(description = "예약 메모", example = "예약 메모입니다.")
    private String reservationMemo;

}
