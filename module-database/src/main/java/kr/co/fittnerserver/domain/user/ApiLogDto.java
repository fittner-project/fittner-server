package kr.co.fittnerserver.domain.user;

import lombok.*;

@Data
public class ApiLogDto {

    private String apiLogId;
    private String mdcId;
    private int reqDate;
    private String reqTime;
    private String header;
    private String clientIp;
    private String callUri;
    private String reqMethod;
    private String contentType;
    private String reqParam;
    private String resDate;
    private String resTime;
    private String resCode;
    private String resResult;

}
