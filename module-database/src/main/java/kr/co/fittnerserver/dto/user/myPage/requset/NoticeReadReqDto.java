package kr.co.fittnerserver.dto.user.myPage.requset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NoticeReadReqDto {

    @Schema(description = "공지사항ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    @NotBlank
    private String noticeId;
}
