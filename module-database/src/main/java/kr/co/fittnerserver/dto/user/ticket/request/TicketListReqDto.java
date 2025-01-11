package kr.co.fittnerserver.dto.user.ticket.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TicketListReqDto {

    @Schema(description = "티켓상태", example = "TOTAL | ING | STOP | ASSIGN_TO | ASSIGN_FROM | REFUND | BEFORE | AFTER")
    @Pattern(regexp = "TOTAL|ING|STOP|ASSIGN_TO|ASSIGN_FROM|REFUND|BEFORE|AFTER")
    @NotBlank
    private String ticketStatus;
}
