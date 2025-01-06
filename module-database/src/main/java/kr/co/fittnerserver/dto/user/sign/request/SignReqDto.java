package kr.co.fittnerserver.dto.user.sign.request;

import lombok.Data;

@Data
public class SignReqDto {

    private String reservationId;
    private String memberId;
    private String fileGroupId;
    private String signType;
    private String signMemo;

}
