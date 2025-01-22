package kr.co.fittnerserver.dto.user.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StatusChkResDto {

    @Schema(description = "트레이너 상태",example = "ACTIVE | INACTIVE | DROP | STOP")
    private String trainerStatus;
}
