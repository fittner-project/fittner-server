package kr.co.fittnerserver.dto.file.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FileResDto {

    @Schema(description = "파일그룹ID", example = "d1e679af-d348-11ef-b7c9-0242ac190002")
    private String fileGroupId;

    @Schema(description = "파일ID", example = "A31IA2A5242")
    private String fileId;

    @Schema(description = "파일명", example = "test(3).jpeg")
    private String fileName;

    @Schema(description = "파일경로", example = "/app/fittner/upload")
    private String filePath;

    @Schema(description = "파일url", example = "https://api.fittner.co.kr/api/v1/common/file/show/2f2CfG55i25")
    private String fileUrl;
}
