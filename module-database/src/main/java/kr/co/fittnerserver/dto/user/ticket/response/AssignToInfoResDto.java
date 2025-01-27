package kr.co.fittnerserver.dto.user.ticket.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AssignToInfoResDto {

    @Schema(description = "이용권ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    private String ticketId;

    @Schema(description = "이용권명", example = "프리미엄PT")
    private String ticketName;

    @Schema(description = "이용권시작일", example = "20250101")
    private String ticketStartDate;

    @Schema(description = "이용권종료일", example = "20250401")
    private String ticketEndDate;

    @Schema(description = "이용권잔여횟수", example = "12")
    private String remainingCnt;

    @Schema(description = "이용권전체횟수", example = "20")
    private String totalCnt;

    @Schema(description = "회원명", example = "아무기")
    private String memberName;

    @Schema(description = "회원휴대폰 끝네자리", example = "1234")
    private String memberPhoneEnd;
}
