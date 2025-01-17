package kr.co.fittnerserver.dto.user.myPage.response;

import lombok.Data;

import java.util.List;

@Data
public class TermsListResDto {

    private String termsTitle;
    private String termsStartDate;
    private List<String> beforeTermsStartDate;
}
