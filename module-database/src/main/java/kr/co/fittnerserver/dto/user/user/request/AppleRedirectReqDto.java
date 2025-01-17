package kr.co.fittnerserver.dto.user.user.request;

import lombok.Data;

@Data
public class AppleRedirectReqDto {
    private String code;
    private String state;
    private String user;
}
