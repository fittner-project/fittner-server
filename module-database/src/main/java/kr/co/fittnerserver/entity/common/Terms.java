package kr.co.fittnerserver.entity.common;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.common.enums.TermsKind;
import kr.co.fittnerserver.entity.common.enums.TermsState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Terms extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "약관 키값")
    private String termsId;

    @Comment(value = "약관 제목")
    @Column(length = 50)
    private String termsTitle;

    @Comment(value = "약관 필수여부")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String termsEssentialYn;

    @Enumerated(EnumType.STRING)
    @Comment(value = "약관 구분")
    @Column(length = 10)
    private TermsKind termsKind;

    @Enumerated(EnumType.STRING)
    @Comment(value = "약관 상태")
    @Column(length = 10)
    private TermsState termsState;

    @Comment(value = "약관 시작일")
    private LocalDate termsStartDate;

    @Comment(value = "약관 종료일")
    private LocalDate termsEndDate;

    @OneToOne
    @JoinColumn(name = "file_group_id")
    private FileGroup fileGroup;

    @OneToOne
    @JoinColumn(name = "terms_change_id")
    private TermsChange termsChange;

}
