package kr.co.fittnerserver.dto.user.ticket.response;

import lombok.Data;

@Data
public class TicketListResDto {
    private String ticketId;
    private String ticketStatus;
    private String memeberName;
    private String ticketName;
    private String ticketStartEndDate;
}
