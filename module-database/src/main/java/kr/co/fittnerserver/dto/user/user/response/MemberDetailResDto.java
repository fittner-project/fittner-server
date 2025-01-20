package kr.co.fittnerserver.dto.user.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.fittnerserver.entity.user.enums.TicketCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDetailResDto {
    @Schema(description = "회원키값", example = "5e1d602e-a038-4293-950d-33d3780d08de")
    private String memberId;
    @Schema(description = "티켓키값", example = "5e1d602e-a038-4293-950d-33d3780d08de")
    private String ticketId;
    @Schema(description = "회원명", example = "김영재")
    private String memberName;
    @Schema(description = "상품명", example = "프리미엄PT20")
    private String productName;
    @Schema(description = "상품 개월수", example = "3")
    private int productMonth;
    @Schema(description = "상품 총횟수", example = "20")
    private int productTotalCnt;
    @Schema(description = "회원의 상품 잔여횟수", example = "15")
    private int productRemainCnt;
    @Schema(description = "회원의 티켓 시작일", example = "20240903")
    private String ticketStartDate;
    @Schema(description = "회원의 티켓 만료일", example = "20241202")
    private String ticketEndDate;
    @Schema(description = "출석 카운트", example = "5")
    private int attendanceCnt;
    @Schema(description = "결석 카운트", example = "0")
    private int absenceCnt;
    @Schema(description = "티켓 상태", example = "기간 중단")
    private TicketCode ticketCode;
}
