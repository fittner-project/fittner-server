package kr.co.fittnerserver.dto.user.myPage.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SalesInfoDetailResDto {

    @Schema(description = "이용권ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    private String ticketId;

    @Schema(description = "회원이름", example = "아무개")
    private String memberName;

    @Schema(description = "예약시작일",example = "20250101")
    private String reservationStartDate;

    @Schema(description = "예약종료일",example = "20250301")
    private String reservationEndDate;

    @Schema(description = "예약시작시간",example = "1600")
    private String reservationStartTime;

    @Schema(description = "예약종료시간",example = "1700")
    private String reservationEndTime;

    @Schema(description = "회차",example = "3")
    private String ticketUseCnt;

    @Schema(description = "수익금",example = "150000")
    private String salesPrice;

    @Schema(description = "예약상태",example = "NOSHOW | SIGN")
    private String reservationStatus;
}
