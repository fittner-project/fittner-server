package kr.co.fittnerserver.dto.user.reservation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GroupedReservationDto {

    @Schema(description = "날짜 끝두자리 값", example = "02")
    private String lastTwoDigits;

    @Schema(description = "예약색상", example = "#FF0000")
    private List<String> reservations; // String 리스트로 변경
}
