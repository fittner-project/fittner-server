package kr.co.fittnerserver.dto.user.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.fittnerserver.entity.user.enums.TicketCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDetailResDto {
    @Schema(description = "회원명", example = "김영재")
    private String memberName;
    @Schema(description = "상품명", example = "프리미엄PT20")
    private String productName;
    @Schema(description = "상품 개월수", example = "3")
    private int productMonth;
    @Schema(description = "상품 총횟수", example = "20")
    private int productCount;
    @Schema(description = "회원의 상품 잔여횟수", example = "15")
    private int productRemainCount;
    @Schema(description = "회원의 상품 시작일", example = "2024-09-03")
    private String productStartDate;
    @Schema(description = "회원의 상품 만료일", example = "2024-12-02")
    private String productEndDate;
    @Schema(description = "출석 카운트", example = "5")
    private int attendanceCount;
    @Schema(description = "결석 카운트", example = "0")
    private int absenceCount;
    @Schema(description = "티켓 상태", example = "기간 중단")
    private TicketCode ticketCode;
}
