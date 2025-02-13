package kr.co.fittnerserver.dto.user.ticket.request;

import lombok.Data;

@Data
public class SuspendTicketReqDto {
    private String ticketId;
    private String suspendReason;
}
