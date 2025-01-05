package kr.co.fittnerserver.dto.user.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CenterListResDto {
    @Schema(description = "센터 ID", example = "1")
    private String centerId;
    @Schema(description = "센터 이름", example = "피트너 센터")
    private String centerName;
    @Schema(description = "센터 주소", example = "서울시 강남구")
    private String centerAddress;
    @Schema(description = "센터 전화번호", example = "02-1234-5678")
    private String centerTel;
    @Schema(description = "센터 이미지", example = "http://www.fittner.co.kr/images/center/1.jpg")
    private List<CenterFileResDto> centerImage;
}
