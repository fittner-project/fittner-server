package kr.co.fittnerserver.dto.user.ticket.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SuspendTicketReqDto {
    @Schema(description = "티켓키값",example = "1234")
    private String ticketId;
    @Schema(description = "티켓일시정지이유",example = "배아파서")
    private String suspendReason;
    @Schema(description = "티켓정지시작일자",example = "20250628")
    private String ticketSuspendStartDate;
    @Schema(description = "티켓정지종료일자",example = "20250728")
    private String ticketSuspendEndDate;
}
