package kr.co.fittnerserver.dto.user.reservation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReservationUpdateReqDto {

    @Schema(description = "예약ID", example = "3c약7ba936-d4d3-11ef-b7c9-0242ac190002")
    @NotBlank
    private String reservationId;

    @Schema(description = "예약 시작일자", example = "20250105")
    @NotBlank
    private String reservationStartDate;

    @Schema(description = "예약 종료일자", example = "20250106")
    @NotBlank
    private String reservationEndDate;

    @Schema(description = "예약 시작시간", example = "2030")
    @NotBlank
    private String reservationStartTime;

    @Schema(description = "예약 종료일자", example = "2130")
    @NotBlank
    private String reservationEndTime;

    @Schema(description = "색상 태그", example = "007AFF")
    @NotBlank
    private String reservationColor;

    @Schema(description = "예약 푸시", example = "before_5m")
    private String reservationPush;

    @Schema(description = "예약 메모", example = "예약 메모입니다.")
    private String reservationMemo;

}
