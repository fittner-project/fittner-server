package kr.co.fittnerserver.domain.user;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.common.FileGroup;
import kr.co.fittnerserver.entity.common.TermsChange;
import kr.co.fittnerserver.entity.common.enums.TermsKind;
import kr.co.fittnerserver.entity.common.enums.TermsState;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

@Data
public class TermsDto extends BaseTimeEntity {

    private String termsId;
    private String termsTitle;
    private String termsEssentialYn;
    private String termsKind;
    private String termsState;
    private String termsStartDate;
    private String termsEndDate;
    private String fileGroupId;
    private String termsChangeId;
    private String fileUrl;

}
