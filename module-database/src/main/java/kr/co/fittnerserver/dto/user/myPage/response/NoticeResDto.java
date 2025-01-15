package kr.co.fittnerserver.dto.user.myPage.response;

import lombok.Data;

@Data
public class NoticeResDto {

    private String noticeId;
    private String noticeTitle;
    private String noticeContent;
    private String noticeReadYn;
    private String noticeDate;
}
