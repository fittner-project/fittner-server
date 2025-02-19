package kr.co.fittnerserver.dto.user.ticket.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TicketDetailResDto {

    @Schema(description = "회원 정보")
    private MemberInfo memberInfo;

    @Schema(description = "이용권 정보")
    private TicketInfo ticketInfo;

    @Schema(description = "환불 정보")
    private RefundInfo refundInfo;

    @Schema(description = "양도 정보")
    private AssignInfo assignInfo;


    @Data
    public static class MemberInfo{

        @Schema(description = "회원이름", example = "아무기")
        private String memberName;

        @Schema(description = "회원핸드폰", example = "01011112222")
        private String memberPhone;

        @Schema(description = "횐원성별", example = "M")
        private String memberGender;

        @Schema(description = "회원생년월일", example = "890122")
        private String memberBirth;

        @Schema(description = "회원주소", example = "서울시 관악구 아무동 22-1")
        private String memberAddress;

        @Schema(description = "회원메모", example = "25년 8월 바디프로필 희망")
        private String memberMomo;

        @Schema(description = "회원가입경로", example = "지인소개")
        private String memberJoinPath;
    }

    @Data
    public static class TicketInfo{

        @Schema(description = "이용권코드", example = "ING | STOP | ASSIGN_TO | ASSIGN_FROM | REFUND | AFTER")
        private String ticketCode;

        @Schema(description = "이용권코드명", example = "이용중")
        private String ticketCodeName;

        @Schema(description = "이용권명", example = "프리미엄PT")
        private String ticketName;

        @Schema(description = "이용권시작일", example = "20250111")
        private String ticketStartDate;

        @Schema(description = "이용권종료일", example = "20250411")
        private String ticketEndDate;

        @Schema(description = "이용권정지시작일", example = "20250111")
        private String ticketSuspendStartDate;

        @Schema(description = "이용권정지종료일", example = "20250411")
        private String ticketSuspendEndDate;

        @Schema(description = "이용권이용횟수", example = "17")
        private String ticketUseCnt;

        @Schema(description = "이용권전체횟수", example = "20")
        private String ticketTotalCnt;

        @Schema(description = "이용권가격", example = "2000000")
        private String ticketPrice;
    }

    @Data
    public static class RefundInfo{

        @Schema(description = "환불횟수", example = "3")
        private String refundCnt;

        @Schema(description = "환불금액", example = "300000")
        private String refundPrice;

        @Schema(description = "환불일시", example = "20250203113022")
        private String refundDateTime;
    }

    @Data
    public static class AssignInfo{

        @Schema(description = "양도센터명", example = "아워피티니스 서초점")
        private String assignCenterName;

        @Schema(description = "양도트레이너명", example = "고길동")
        private String assignTrainerName;

        @Schema(description = "양도일", example = "20250103")
        private String assignDate;

        @Schema(description = "양도횟수", example = "6")
        private String assignCnt;

        @Schema(description = "양도회원명", example = "김철수")
        private String assignMemberName;
    }



}
