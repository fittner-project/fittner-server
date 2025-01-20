package kr.co.fittnerserver.dto.file.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FileReqDto {

    @Schema(description = "암호화 여부",example = "N")
    private String encryptYn = "N"; //default "N"
}
