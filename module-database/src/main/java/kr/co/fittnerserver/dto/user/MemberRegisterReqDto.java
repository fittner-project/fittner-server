package kr.co.fittnerserver.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MemberRegisterReqDto {
    @Schema(description = "회원 이름", example = "홍길동")
    private String memberName;
    @Schema(description = "회원 전화번호", example = "01012345678")
    private String memberPhone;
    @Schema(description = "회원 성별", example = "M")
    private String memberGender;
    @Schema(description = "회원 생년월일", example = "960615")
    private String memberBirth;
    @Schema(description = "회원 주소", example = "서울시 강남구")
    private String memberAddress;
    @Schema(description = "상품명", example = "프리미엄PT20")
    private String productName;
    @Schema(description = "상품 시작일", example = "2021-01-01")
    private String productStartDate;
    @Schema(description = "상품 종료일", example = "2021-12-31")
    private String productEndDate;
    @Schema(description = "상품 횟수", example = "20")
    private int productCount;
    @Schema(description = "상품 가격", example = "100000")
    private int productPrice;
    @Schema(description = "회원 메모", example = "특이사항 없음")
    private String memberMemo;
    @Schema(description = "가입 경로", example = "지인 소개")
    private String memberJoinPath;




}
