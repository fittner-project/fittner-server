package kr.co.fittnerserver.dto.user.myPage.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MyPageInfoResDto {

    @Schema(description = "트레이너 이름",example = "아무개")
    private String trainerName;

    @Schema(description = "트레이너 이메일",example = "test001@naver.com")
    private String trainerEmail;

    @Schema(description = "트레이너 sns종류",example = "APPLE | GOOGLE | KAKAO")
    private String trainerSnsKind;
}
