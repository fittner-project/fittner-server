package kr.co.fittnerserver.entity.user;

import jakarta.persistence.*;
import kr.co.fittnerserver.dto.user.JoinReqDto;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.user.enums.TrainerSnsKind;
import kr.co.fittnerserver.entity.user.enums.TrainerStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Trainer extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "트레이너 키값")
    private String trainerId;
    @Comment(value = "트레이너 휴대폰")
    @Column(length = 100)
    private String trainerPhone;
    @Comment(value = "트레이너 이름")
    @Column(length = 30)
    private String trainerName;
    @Enumerated(EnumType.STRING)
    @Comment(value = "트레이너 상태")
    @Column(length = 10)
    private TrainerStatus trainerStatus;
    @Comment(value = "트레이너 이메일")
    @Column(length = 100)
    private String trainerEmail;
    @Comment(value = "트레이너 소셜로그인 종류")
    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private TrainerSnsKind trainerSnsKind;
    @Comment(value = "CI")
    @Column(length = 150)
    private String trainerCiNo;
    @Comment(value = "트레이너 탈퇴유무")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String trainerDropYn;
    @Comment(value = "트레이너 FCM TOKEN")
    @Column(length = 200)
    private String trainerFcmToken;
    @Comment(value = "트레이너 상품 변경 권힌")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String trainerProductChangeYn;
    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    public Trainer(JoinReqDto joinReqDto,Center center) throws Exception {
        this.trainerPhone = joinReqDto.getTrainerPhone();
        this.trainerName = joinReqDto.getTrainerName();
        this.trainerEmail = joinReqDto.getTrainerEmail();
        this.trainerSnsKind = joinReqDto.getTrainerSnsKind();
        this.trainerStatus = TrainerStatus.INACTIVE;
        this.trainerDropYn = "N";
        this.trainerProductChangeYn = "N";
        this.trainerFcmToken = joinReqDto.getTrainerFcmToken();
        this.trainerCiNo = joinReqDto.getTrainerCiNo();
        this.center = center;
    }
}
