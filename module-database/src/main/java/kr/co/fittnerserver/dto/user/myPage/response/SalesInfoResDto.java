package kr.co.fittnerserver.dto.user.myPage.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SalesInfoResDto {

    @Schema(description = "이용권ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    private String ticketId;

    @Schema(description = "회원ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    private String memberId;

    @Schema(description = "회원이름",example = "아무개")
    private String memberName;

    @Schema(description = "이용권명",example = "프리미엄PT")
    private String ticketName;

    @Schema(description = "이용권시작일",example = "20250101")
    private String ticketStartDate;

    @Schema(description = "이용권종료일",example = "20250301")
    private String ticketEndDate;

    @Schema(description = "회차",example = "3")
    private String ticketUseCnt;

    @Schema(description = "현재수익금",example = "150000")
    private String nowSalesPrice;
}
