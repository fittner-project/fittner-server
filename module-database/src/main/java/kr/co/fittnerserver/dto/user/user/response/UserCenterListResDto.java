package kr.co.fittnerserver.dto.user.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCenterListResDto {
    @Schema(description = "센터 가입 ID", example = "1")
    private String centerJoinId;
    @Schema(description = "센터 ID", example = "1")
    private String centerId;
    @Schema(description = "센터 이름", example = "피트너 센터")
    private String centerName;
    @Schema(description = "센터 주소", example = "서울시 강남구")
    private String centerAddress;
    @Schema(description = "센터 승인여부", example = "N")
    private String centerJoinApprovalYn;
    @Schema(description = "주 센터 여부", example = "Y")
    private String centerJoinMainYn;


}
