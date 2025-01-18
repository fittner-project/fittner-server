package kr.co.fittnerserver.dto.user.ticket.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RelayReqDto {

    @Schema(description = "회원ID", example = "3c7ba936-d4d3-11ef-b7c9-0242ac190002")
    @NotBlank
    private String memberId;

    @Schema(description = "상품명", example = "프리미엄PT20")
    @NotBlank
    private String productName;

    @Schema(description = "상품 시작일", example = "20210101")
    @NotBlank
    private String productStartDate;

    @Schema(description = "상품 종료일", example = "20211231")
    @NotBlank
    private String productEndDate;

    @Schema(description = "상품 횟수", example = "20")
    @NotBlank
    private String productCount;

    @Schema(description = "상품 가격", example = "100000")
    @NotBlank
    private String productPrice;

    @Schema(description = "회원 메모", example = "특이사항 없음")
    private String memberMemo;

    @Schema(description = "가입 경로", example = "지인 소개")
    private String memberJoinPath;


}
