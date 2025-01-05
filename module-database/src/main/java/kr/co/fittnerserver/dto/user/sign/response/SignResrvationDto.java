package kr.co.fittnerserver.dto.user.sign.response;

import lombok.Data;

@Data
public class SignResrvationDto {

    private String memberName;
    private String reservationUseCnt;
    private String reservationTime;
    private String reservationMemo;

}
