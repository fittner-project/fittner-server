package kr.co.fittnerserver.dto.user.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerResDto {
    @Schema(description = "전체 갯수", example = "5")
    private int trainerTotal;
    @Schema(description = "트레이너이름", example = "김영재")
    private List<TrainerResultDto> trainerInfo;
}
