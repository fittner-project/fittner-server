package kr.co.fittnerserver.entity.user;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.user.enums.MemberGender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "회원 키값")
    private String memberId;
    @Comment(value = "회원 이름")
    @Column(length = 30)
    private String memberName;
    @Comment(value = "회원 휴대폰")
    @Column(length = 30)
    private String memberPhone;
    @Enumerated(EnumType.STRING)
    @Comment(value = "회원 성별")
    @Column(length = 1)
    private MemberGender memberGender;
    @Comment(value = "회원 생년월일 (YYMMDD)")
    @Column(length = 6)
    private String memberBirth;
    @Comment(value = "회원 주소")
    @Column(length = 100)
    private String memberAddress;
    @Comment(value = "회원 삭제여부")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String memberDeleteYn;
    @Comment(value = "회원 삭제일시")
    private LocalDateTime memberDeleteDatetime;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
}
