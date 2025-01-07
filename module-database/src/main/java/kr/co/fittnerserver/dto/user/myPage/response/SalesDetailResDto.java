package kr.co.fittnerserver.dto.user.myPage.response;

import lombok.Data;

@Data
public class SalesDetailResDto {

    private String memeberName;
    private String ticetName;
    private String reservationStartDate;
    private String reservationStartEndTime;
    private String ticketUseCnt;
    private String reservationStatus;
    private String settlementAmount;
}
