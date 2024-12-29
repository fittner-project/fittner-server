package kr.co.fittnerserver.entity.admin;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.user.Member;
import kr.co.fittnerserver.entity.user.Trainer;
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
public class TrainerAssignment extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "트레이너 변경 키값")
    private String trainerAssignmentId;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    @Comment(value = "트레이너 키값")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Comment(value = "회원 키값")
    private Member member;

    @Comment(value = "센터 키값")
    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    @Comment(value = "트레이너 변경 노출 여부")
    @Column(length = 1, columnDefinition = "char(1) default 'Y'")
    private String trainerAssignmentShowYn;
}
