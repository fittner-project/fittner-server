package kr.co.fittnerserver.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CenterFileResDto {
    private String fileUrl;

    public CenterFileResDto(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
