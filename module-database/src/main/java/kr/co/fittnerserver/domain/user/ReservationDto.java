package kr.co.fittnerserver.domain.user;

import lombok.*;

@Data
public class ReservationDto {

    private String reservationId;
    private String reservationStartDate;
    private String reservationEndDate;
    private String reservationStartTime;
    private String reservationEndTime;
    private String reservationMemo;
    private String reservationColor;
    private String reservationDeleteYn;
    private String reservationStatus;
    private String trainerSettlementAmount;
    private String trainerSettlementChangeAmount;
    private String reservationPush;
    private String trainerSettlementId;
    private String centerId;
    private String trainerId;
    private String memberId;
    private String ticketId;


}
