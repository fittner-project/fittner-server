package kr.co.fittnerserver.dto.user.ticket.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefundReqDto {

    @Schema(description = "이용권ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    @NotBlank
    private String ticketId;

}
