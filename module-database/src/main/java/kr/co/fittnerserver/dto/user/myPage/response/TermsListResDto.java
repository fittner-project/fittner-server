package kr.co.fittnerserver.dto.user.myPage.response;

import lombok.Data;

import java.util.List;

@Data
public class TermsListResDto {

    private String ingTermsTitle;
    private String intTermsStartDate;
    private List<TotalTerm> totalTermList;

    @Data
    public static class TotalTerm{
        private String termsStartDate;
        private String termsUrl;
    }
}
