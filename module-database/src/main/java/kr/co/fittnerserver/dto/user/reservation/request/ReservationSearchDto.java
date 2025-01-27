package kr.co.fittnerserver.dto.user.reservation.request;

import lombok.Data;

@Data
public class ReservationSearchDto {
    private String reservationStartDate;
    private String reservationEndDate;
}
