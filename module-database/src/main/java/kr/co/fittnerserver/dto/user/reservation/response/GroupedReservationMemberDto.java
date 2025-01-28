package kr.co.fittnerserver.dto.user.reservation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GroupedReservationMemberDto {
    @Schema(description = "날짜 끝두자리 값", example = "02")
    private String lastTwoDigits;
    @Schema(description = "예액 정보들", example = " ")
    private List<ReservationMemberResDto> reservations;
}
