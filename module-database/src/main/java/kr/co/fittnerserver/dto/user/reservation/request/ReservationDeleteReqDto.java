package kr.co.fittnerserver.dto.user.reservation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReservationDeleteReqDto {

    @Schema(description = "예약ID", example = "3c7ba936-d4d3-11ef-b7c9-0242ac190002")
    @NotBlank
    private String reservationId;

}
