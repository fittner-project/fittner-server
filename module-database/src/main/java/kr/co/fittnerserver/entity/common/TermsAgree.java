package kr.co.fittnerserver.entity.common;

import jakarta.persistence.*;
import kr.co.fittnerserver.dto.user.user.request.AgreementDto;
import kr.co.fittnerserver.entity.user.Trainer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class TermsAgree extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "약관동의 키값")
    private String termsAgreeId;

    @Comment(value = "동의여부")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String termsAgreeYn;

    @Comment(value = "약관 동의일시")
    private String termsAgreeDateTime;

    @ManyToOne
    @JoinColumn(name = "terms_id")
    private Terms terms;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    //TODO 대표앱 매핑

    public TermsAgree(Terms terms, String agreed, Trainer trainer) {
        this.termsAgreeYn = agreed;
        this.termsAgreeDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.terms = terms;
        this.trainer = trainer;
    }

}
