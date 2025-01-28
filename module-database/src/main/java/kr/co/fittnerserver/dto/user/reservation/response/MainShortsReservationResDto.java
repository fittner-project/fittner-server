package kr.co.fittnerserver.dto.user.reservation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class MainShortsReservationResDto {
    @Schema(description = "날짜 끝두자리 값", example = "02")
    private String lastTwoDigits;
    @Schema(description = "예약색상", example = "#FF0000")
    private String reservationColor;

    public MainShortsReservationResDto(String lastTwoDigits , String reservationColor) {
        this.lastTwoDigits = lastTwoDigits;
        this.reservationColor = reservationColor;
    }
}
