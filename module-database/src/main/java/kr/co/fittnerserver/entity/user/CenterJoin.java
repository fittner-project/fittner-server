package kr.co.fittnerserver.entity.user;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
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
public class CenterJoin extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "센터 등록 키값")
    private String centerJoinId;
    @Comment(value = "센터 승인유무")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String centerJoinApprovalYn;
    @Comment(value = "주센터 여부")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String centerJoinMainYn;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
}
