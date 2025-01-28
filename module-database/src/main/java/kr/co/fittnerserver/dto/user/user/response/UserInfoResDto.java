package kr.co.fittnerserver.dto.user.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserInfoResDto {

    @Schema(description = "트레이너email", example = "test002@naver.com")
    private String trainerEmail;

    @Schema(description = "트레이너 소셜구분", example = "APPLE | GOOGLE | KAKAO")
    private String trainerSnsKind;

    @Schema(description = "트레이너명", example = "아무기")
    private String trainerName;

}
