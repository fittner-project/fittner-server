package kr.co.fittnerserver.dto.user.myPage.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SalesResDto {

    @Schema(description = "예상수익금",example = "1000000")
    private String projectionSalesPrice;

    @Schema(description = "현재수익금",example = "970000")
    private String nowSalesPrice;
}
