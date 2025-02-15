package kr.co.fittnerserver.dto.user.myPage.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SalesInfoDetailResDto {

    @Schema(description = "예약월")
    public String reservationMonth;

    @Schema(description = "수업리스트")
    public List<SalesInfoDetailDto> reservationListList;

}
