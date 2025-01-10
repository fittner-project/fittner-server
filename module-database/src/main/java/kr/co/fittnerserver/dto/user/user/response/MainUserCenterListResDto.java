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
public class MainUserCenterListResDto {
    @Schema(description = "센터 가입 ID", example = "1")
    private String centerJoinId;
    @Schema(description = "센터 이름", example = "피트너 센터")
    private String centerName;
}
