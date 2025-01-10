package kr.co.fittnerserver.dto.user.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CenterRegisterReqDto {
    @Schema(description = "센터 키값", example = "e7d5eeb6-cda6-11ef-b7c9-0242ac190002")
    private String centerId;
}
