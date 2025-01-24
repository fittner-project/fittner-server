package kr.co.fittnerserver.dto.user.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BrandColorResDto {

    @Schema(description = "그레이컬러1", example = "헥사값")
    private String greyTypeA;

    @Schema(description = "그레이컬러2", example = "헥사값")
    private String greyTypeB;

    @Schema(description = "그레이컬러3", example = "헥사값")
    private String greyTypeC;

    @Schema(description = "그레이컬러4", example = "헥사값")
    private String greyTypeD;

    @Schema(description = "브랜드컬러", example = "헥사값")
    private String primary;

    @Schema(description = "브랜드 서브컬러", example = "헥사값")
    private String sub;

    @Schema(description = "텍스트컬러1", example = "헥사값")
    private String textTypeA;

    @Schema(description = "텍스트컬러2", example = "헥사값")
    private String textTypeB;

    @Schema(description = "텍스트컬러3", example = "헥사값")
    private String textTypeC;

    @Schema(description = "텍스트컬러4", example = "헥사값")
    private String textTypeD;

    @Schema(description = "텍스트컬러5", example = "헥사값")
    private String textTypeE;

    @Schema(description = "텍스트컬러6", example = "헥사값")
    private String textTypeF;

    @Schema(description = "텍스트컬러7", example = "헥사값")
    private String textTypeG;

}
