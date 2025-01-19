package kr.co.fittnerserver.dto.user.ticket.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RefundInfoResDto {

    @Schema(description = "이용권ID",example = "3803f0e5-d4d3-11ef-b7c9-0242ac190002")
    private String ticketId;

    @Schema(description = "회원이름",example = "아무개")
    private String memberName;

    @Schema(description = "이용권이름",example = "프리미엄PT")
    private String ticketName;

    @Schema(description = "이용권 시작일자",example = "20241101")
    private String ticketStartDate;

    @Schema(description = "이용권 종료일자",example = "20250101")
    private String ticketEndDate;

    @Schema(description = "이용권 횟수",example = "20")
    private String ticketTotalCnt;

    @Schema(description = "이용권 남은 횟수",example = "5")
    private String ticketRemainingCnt;

    @Schema(description = "이용권 금액",example = "2000000")
    private String ticketPrice;

    @Schema(description = "이용권 사용금액",example = "1500000")
    private String ticketUsePrice;

    @Schema(description = "환불 예상금액",example = "500000")
    private String refundPrice;

}
