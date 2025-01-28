package kr.co.fittnerserver.dto.user.reservation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class ReservationMemberResDto {
    @Schema(description = "날짜 끝두자리 값", example = "02")
    private String lastTwoDigits;
    @Schema(description = "회원명", example = "김영재")
    private String memberName;
    @Schema(description = "예약시작일", example = "20250101")
    private String reservationStartDate;
    @Schema(description = "예약종료일", example = "20250110")
    private String reservationEndDate;
    @Schema(description = "예약시작시간", example = "0900")
    private String reservationStartTime;
    @Schema(description = "예약종료시간", example = "1000")
    private String reservationEndTime;
    @Schema(description = "예약색상", example = "#FF0000")
    private String reservationColor;
    @Schema(description = "예약메모", example = "헬스PT")
    private String reservationMemo;
    @Schema(description = "PTN회차", example = "1")
    private int ptnCnt;

    public ReservationMemberResDto(String lastTwoDigits, String memberName, String reservationStartDate, String reservationEndDate, String reservationStartTime, String reservationColor ,String reservationEndTime, String reservationMemo, int ptnCnt) {
        this.lastTwoDigits = lastTwoDigits;
        this.memberName = memberName;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
        this.reservationStartTime = reservationStartTime;
        this.reservationEndTime = reservationEndTime;
        this.reservationColor = reservationColor;
        this.reservationMemo = reservationMemo;
        this.ptnCnt = ptnCnt;
    }
}
