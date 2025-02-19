package kr.co.fittnerserver.dto.user.ticket.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TicketListResDto {

    @Schema(description = "이용권ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    private String ticketId;

    @Schema(description = "이용권코드", example = "ING | STOP | ASSIGN_TO | ASSIGN_FROM | REFUND | AFTER")
    private String ticketCode;

    @Schema(description = "회원이름", example = "아무기")
    private String memberName;

    @Schema(description = "이용권명", example = "프리미엄PT")
    private String ticketName;

    @Schema(description = "이용권시작일", example = "20250103")
    private String ticketStartDate;

    @Schema(description = "이용권종료일", example = "20250401")
    private String ticketEndDate;

    @Schema(description = "회원 휴대폰뒤 4자리", example = "1422")
    private String memberPhoneEnd;

    @Schema(description = "이용권 전체 횟수", example = "20")
    private String ticketTotalCnt;
}
