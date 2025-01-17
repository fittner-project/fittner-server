package kr.co.fittnerserver.dto.user.assign.response;

import lombok.Data;

@Data
public class AssignToInfoResDto {

    private String ticketId;
    private String ticketName;
    private String ticketStartDate;
    private String ticketEndDate;
    private String remainingCnt;
    private String totalCnt;
    private String memberName;
    private String memberPhoneEnd;
}
