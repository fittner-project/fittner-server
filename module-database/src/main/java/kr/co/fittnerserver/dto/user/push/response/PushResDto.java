package kr.co.fittnerserver.dto.user.push.response;

import lombok.Data;

@Data
public class PushResDto {

    private String pushId;
    private String pushTitle;
    private String pushContent;
    private String pushDate;
    private String pushReadYn;

}
