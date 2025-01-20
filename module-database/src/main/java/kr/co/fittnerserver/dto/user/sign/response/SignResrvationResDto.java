package kr.co.fittnerserver.dto.user.sign.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SignResrvationResDto {

    @Schema(description = "예약 총갯수", example = "3")
    private String reservationTotalCnt;

    @Schema(description = "예약 데이터", example = "")
    private List<SignResrvationDto> reservationList;

}
