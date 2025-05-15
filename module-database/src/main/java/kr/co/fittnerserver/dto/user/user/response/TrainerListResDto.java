package kr.co.fittnerserver.dto.user.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.fittnerserver.entity.user.enums.MemberGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerListResDto {
    @Schema(description = "전체 갯수", example = "5")
    private long trainerTotal;

    @Schema(description = "트레이너이름", example = "김영재")
    private String trainerName;


}
