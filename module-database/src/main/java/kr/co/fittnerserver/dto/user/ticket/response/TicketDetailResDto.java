package kr.co.fittnerserver.dto.user.ticket.response;

import lombok.Data;

@Data
public class TicketDetailResDto {

    private String ticketId;
    private String memeberName;
    private String memberPhone;
    private String memberGender;
    private String memberBirth;
    private String memberAddress;
    private String memberMomo;
    private String memberJoinPath;
    private String ticketCode;
    private String ticketName;
    private String ticketStartDate;
    private String ticketEndDate;
    private String remainingCnt;
    private String ticketPrice;

    //환불관련
    private String refundId;
    private String refundCnt;
    private String refundPrice;
    private String refundDateTime;

    //양도관련
    private String originalTicketId;
    private String assignCenterName;
    private String assignTrainerName;
    private String assignDate;
    private String assignCnt;
    private String assignName;
}
