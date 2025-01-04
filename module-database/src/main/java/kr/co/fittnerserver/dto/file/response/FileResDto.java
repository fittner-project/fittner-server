package kr.co.fittnerserver.dto.file.response;

import lombok.Data;

@Data
public class FileResDto {

    private String groupFileId;

    private String fileId;

    private String fileName;

    private String filePath;

    private String fileUrl;
}
