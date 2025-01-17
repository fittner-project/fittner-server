package kr.co.fittnerserver.domain.user;

import lombok.*;

@Data
public class TicketDto {

    //이용권 정보
    private String ticketId;
    private String ticketStartDate;
    private String ticketEndDate;
    private String ticketCode;
    private String ticketCodeName;
    private String ticketSuspendStartDate;
    private String ticketSuspendEndDate;
    private String ticketUseCnt;
    private String originalTicketId;
    private String trainerProductId;
    private String ticketName;
    private String ticketPrice;
    private String ticketTotalCnt;
    private String createdDate;

    //회원 정보
    private String memberId;
    private String memberName;
    private String memberPhone;
    private String memberPhoneEnd;
    private String memberBirth;
    private String memberMemo;
    private String memberJoinPath;
    private String memberGender;
    private String memberAdress;

    //센터정보
    private String centerId;
    private String centerName;
    private String centerTel;
    private String centerAdress;

    //트레이너정보
    private String trainerId;
    private String trainerName;
    private String trainerEmail;
    private String trainerPhone;

}
