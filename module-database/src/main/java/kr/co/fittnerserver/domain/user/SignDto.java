package kr.co.fittnerserver.domain.user;

import lombok.*;

@Data
public class SignDto {

    private String signId;
    private String signMemo;
    private byte[] signImage;
    private String signType;
    private String reservationId;
    private String memberId;
    private String fileGroupId;
}
