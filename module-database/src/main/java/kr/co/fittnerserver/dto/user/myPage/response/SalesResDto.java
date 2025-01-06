package kr.co.fittnerserver.dto.user.myPage.response;

import lombok.Data;

@Data
public class SalesResDto {

    private String memeberName;
    private String ticetName;
    private String ticketStartEndDate;
    private String ticketStartEndTime;
    private String ticketUseCnt;
    private String totalSettlementAmount;
}
