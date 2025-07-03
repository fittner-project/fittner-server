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
public class TrainerResultDto {
    @Schema(description = "트레이너키값", example = "키값")
    private String trainerId;
    @Schema(description = "트레이너이름", example = "김영재")
    private String trainerName;
    @Schema(description = "트레이너폰번호", example = "김영재")
    private String trainerPhone;
}
