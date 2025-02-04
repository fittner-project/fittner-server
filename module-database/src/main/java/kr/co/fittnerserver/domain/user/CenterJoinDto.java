package kr.co.fittnerserver.domain.user;

import lombok.*;

@Data
public class CenterJoinDto {

    private String centerId;
    private String centerName;
    private String businessNo;
    private String centerAddress;
    private String centerType;
    private String centerOwnerName;
    private String centerTel;
    private String centerDeleteYn;
    private String centerGroupId;
    private String fileGroupId;
    private String centerJoinApprovalYn;
    private String centerJoinMainYn;
    private String trainerId;

}
