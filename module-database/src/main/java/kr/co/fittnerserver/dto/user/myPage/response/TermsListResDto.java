package kr.co.fittnerserver.dto.user.myPage.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class TermsListResDto {

    @Schema(description = "약관제목", example = "개인정보처리방침")
    private String ingTermsTitle;

    @Schema(description = "약관시작일", example = "20250104")
    private String intTermsStartDate;

    @Schema(description = "약관리스트")
    private List<TotalTerm> totalTermList;

    @Data
    public static class TotalTerm{

        @Schema(description = "약관시작일", example = "20250104")
        private String termsStartDate;

        @Schema(description = "약관url", example = "https://api.fittner.co.kr/api/v1/common/file/show/b71iFb5ij6J")
        private String termsUrl;
    }
}
