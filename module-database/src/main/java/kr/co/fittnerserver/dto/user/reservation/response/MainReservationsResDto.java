package kr.co.fittnerserver.dto.user.reservation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MainReservationsResDto {
    @Schema(description = "n번째날", example = "1")
    private int index;
    @Schema(description = "날짜 끝4자리 값", example = "0102")
    private String lastTwoDigits;
    @Schema(description = "예약색상", example = "#FF0000")
    private String reservationColor;
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
    @Schema(description = "전체횟차", example = "10")
    private int totalCnt;
    @Schema(description = "n회차(시작할)", example = "1")
    private int nCnt;

    public MainReservationsResDto(String lastTwoDigits, String reservationColor, String memberName, String reservationStartDate, String reservationEndDate, String reservationStartTime, String reservationEndTime, int totalCnt, int nCnt) {
        this.lastTwoDigits = lastTwoDigits;
        this.reservationColor = reservationColor;
        this.memberName = memberName;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
        this.reservationStartTime = reservationStartTime;
        this.reservationEndTime = reservationEndTime;
        this.totalCnt = totalCnt;
        this.nCnt = nCnt;
    }
}
