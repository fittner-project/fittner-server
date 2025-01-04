package kr.co.fittnerserver.domain.common;

import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import lombok.*;

@Data
public class FileDto extends BaseTimeEntity {

    private String fileId;
    private String fileName;
    private String filePath;
    private String fileUrl;
    private String fileDeleteYn;
    private String fileGroupId;

}
