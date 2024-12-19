package kr.co.fittnerserver.entity.admin;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.admin.enums.CenterType;
import kr.co.fittnerserver.entity.admin.enums.TrainerSettlementCode;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
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
public class TrainerSettlement extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "트레이너 정산 키값")
    private String trainerSettlementId;
    @Comment(value = "트레이너 정산 퍼센트 비율")
    @Column(length = 10)
    private String trainerSettlementPercent;
    @Enumerated(EnumType.STRING)
    @Comment(value = "트레이너 정산 코드")
    @Column(length = 20)
    private TrainerSettlementCode trainerSettlementCode;
    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

}
