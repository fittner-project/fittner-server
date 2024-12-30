package kr.co.fittnerserver.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.fittnerserver.entity.user.enums.TrainerSnsKind;
import lombok.Data;

@Data
public class JoinReqDto {
    @Schema(description = "트레이너 휴대폰 번호", example = "01012345678")
    private String trainerPhone;
    @Schema(description = "트레이너 이름", example = "홍길동")
    private String trainerName;
    @Schema(description = "트레이너 이메일", example = "newy12@naver.com")
    private String trainerEmail;
    @Schema(description = "소셜 로그인 종류", example = " KAKAO || APPLE || GOOGLE")
    private TrainerSnsKind trainerSnsKind;
    @Schema(description = "fcm 토큰 (현재는 빈값으로 넣으세요)", example = "")
    private String trainerFcmToken;
    @Schema(description = "CI NUM (현재는 빈값으로 넣으세요)", example = "")
    private String trainerCiNo;
    @Schema(description = "센터 지점 키값", example = "1")
    private String centerId;
}
