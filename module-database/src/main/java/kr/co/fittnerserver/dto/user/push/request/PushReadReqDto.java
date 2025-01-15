package kr.co.fittnerserver.dto.user.push.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PushReadReqDto {

    @Schema(description = "푸시ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    @NotBlank
    private String pushId;
}
