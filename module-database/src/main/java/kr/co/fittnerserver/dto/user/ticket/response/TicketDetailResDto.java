package kr.co.fittnerserver.dto.user.ticket.response;

import lombok.Data;

@Data
public class TicketDetailResDto {

    //회원 정보
    private String memeberName;
    private String memberPhone;
    private String memberGender;
    private String memberBirth;
    private String memberAddress;
    private String memberMomo;
    private String memberJoinPath;

    //이용권 정보
    private String ticketCodeName;
    private String ticketName;
    private String ticketStartDate;
    private String ticketEndDate;
    private String ticketUseCnt;
    private String ticketTotalCnt;
    private String ticketPrice;

    //환불 정보
    private String refundCnt;
    private String refundPrice;
    private String refundDateTime;

    //양도 정보
    private String assignCenterName;
    private String assignTrainerName;
    private String assignDate;
    private String assignCnt;
    private String assignMemberName;
}
