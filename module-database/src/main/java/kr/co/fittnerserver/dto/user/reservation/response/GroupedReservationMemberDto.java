package kr.co.fittnerserver.dto.user.reservation.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GroupedReservationMemberDto {
    private String lastTwoDigits;
    private List<ReservationMemberResDto> reservations;
}
