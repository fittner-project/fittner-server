package kr.co.fittnerserver.dto.user.myPage.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class NoticeResDto {

    @Schema(description = "공지사항ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    private String noticeId;

    @Schema(description = "공지사항제목", example = "앱 긴급점검 안내")
    private String noticeTitle;

    @Schema(description = "공지사항내용", example = "안녕하세요 피트너입니다. 내부 시스템 점검으로 인해 14:00~14:10(10분) 동안 환불 서비스 중지 예정입니다. 서비스 이용에 불편을 드려 죄송합니다.")
    private String noticeContent;

    @Schema(description = "공지사항읽음여부", example = "N")
    private String noticeReadYn;

    @Schema(description = "공지일자", example = "20250101")
    private String noticeDate;
}
