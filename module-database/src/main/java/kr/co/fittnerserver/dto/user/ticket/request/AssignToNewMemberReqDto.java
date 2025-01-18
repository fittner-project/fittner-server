package kr.co.fittnerserver.dto.user.ticket.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssignToNewMemberReqDto {

    @Schema(description = "원이용권ID", example = "3803f0e5-d4d3-11ef-b7c9-0242ac190002")
    @NotBlank
    private String originalTicketId;

    @Schema(description = "센터ID", example = "3803f0e5-d4d3-11ef-b7c9-0242ac190002")
    @NotBlank
    private String centerId;

    @Schema(description = "트레이너ID", example = "3803f0e5-d4d3-11ef-b7c9-0242ac190002")
    @NotBlank
    private String trainerId;

    @Schema(description = "회원 이름", example = "홍길동")
    @NotBlank
    private String memberName;

    @Schema(description = "회원 전화번호", example = "01012345678")
    @NotBlank
    private String memberPhone;

    @Schema(description = "회원 성별", example = "M")
    @NotBlank
    private String memberGender;

    @Schema(description = "회원 생년월일", example = "960615")
    @NotBlank
    private String memberBirth;

    @Schema(description = "회원 주소", example = "서울시 강남구")
    @NotBlank
    private String memberAddress;

    @Schema(description = "트레이너 상품 키값", example = "3803f0e5-d4d3-11ef-b7c9-0242ac190002")
    @NotBlank
    private String trainerProductId;

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
