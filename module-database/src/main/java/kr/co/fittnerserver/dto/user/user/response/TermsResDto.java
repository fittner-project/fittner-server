package kr.co.fittnerserver.dto.user.user.response;

import lombok.Data;

@Data
public class TermsResDto {
    private String termsId;
    private String termsKind;
    private String termsEssentialYn;
    private String termsTitle;
    private String termsUrl;
}
