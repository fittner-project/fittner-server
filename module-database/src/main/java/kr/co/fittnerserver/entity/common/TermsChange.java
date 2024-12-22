package kr.co.fittnerserver.entity.common;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.common.enums.TermsKind;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class TermsChange extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "약관변경 키값")
    private String termsChangeId;

    @Comment(value = "약관 제목")
    @Column(length = 50)
    private String termsTitle;

    @Enumerated(EnumType.STRING)
    @Comment(value = "약관 구분")
    @Column(length = 10)
    private TermsKind termsKind;

    @Comment(value = "약관 변경 시작일")
    private LocalDate termsChangeStartDate;

    @Comment(value = "약관 변경 종료일")
    private LocalDate termsChangeEndDate;

    @Comment(value = "약관 변경 삭제유무")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String termsChangeDelYn;

    @ManyToOne
    @JoinColumn(name = "file_group_id")
    private FileGroup fileGroup;

}
