package kr.co.fittnerserver.dto.user.reservation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainReservationResDto {
    @Schema(description = "인덱스", example = "1")
    private int index;
    private MainReservationsResDto reservation;
}
