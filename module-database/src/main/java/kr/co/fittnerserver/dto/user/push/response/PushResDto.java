package kr.co.fittnerserver.dto.user.push.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PushResDto {

    @Schema(description = "알림ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    private String pushId;

    @Schema(description = "알림제목", example = "수업 5분전 알림")
    private String pushTitle;

    @Schema(description = "알림내용", example = "아무개 수업이 5분전이에요!")
    private String pushContent;

    @Schema(description = "알림일자", example = "20250122")
    private String pushDate;

    @Schema(description = "알림읽음여부", example = "N")
    private String pushReadYn;

}
