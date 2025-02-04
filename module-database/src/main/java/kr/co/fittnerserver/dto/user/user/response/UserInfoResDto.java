package kr.co.fittnerserver.dto.user.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoResDto {

    @Schema(description = "트레이너 기본정보")
    private DefaultInfo defaultInfo;

    @Schema(description = "트레이너 센터정보")
    private List<CenterInfo> centerInfo;

    @Data
    public static class DefaultInfo {
        @Schema(description = "트레이너Id",example = "3803f0e5-d4d3-11ef-b7c9-0242ac190002")
        private String trainerId;

        @Schema(description = "트레이너 이름",example = "아무개")
        private String trainerName;

        @Schema(description = "트레이너 이메일",example = "test001@naver.com")
        private String trainerEmail;

        @Schema(description = "트레이너 sns종류",example = "APPLE | GOOGLE | KAKAO")
        private String trainerSnsKind;
    }

    @Data
    public static class CenterInfo {
        @Schema(description = "센터그룹Id",example = "3803f0e5-d4d3-11ef-b7c9-0242ac190002")
        private String centerGroupId;

        @Schema(description = "센터Id",example = "3803f0e5-d4d3-11ef-b7c9-0242ac190002")
        private String centerId;

        @Schema(description = "센터명",example = "아워피티니스")
        private String centerName;

        @Schema(description = "주센터여부",example = "Y")
        private String centerJoinMainYn;
    }

}
