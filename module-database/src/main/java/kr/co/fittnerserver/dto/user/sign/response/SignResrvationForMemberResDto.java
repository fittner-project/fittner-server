package kr.co.fittnerserver.dto.user.sign.response;

import lombok.Data;

@Data
public class SignResrvationForMemberResDto {

    private String reservationId;
    private String memberId;
    private String memberName;
    private String reservationStatus;
    private String reservationTime;
    private String reservationUseCnt;

}
