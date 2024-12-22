package kr.co.fittnerserver.entity.common;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.common.enums.PushKind;
import kr.co.fittnerserver.entity.user.Trainer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class PushSet extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "푸시세팅 키값")
    private String pushSetId;

    @Enumerated(EnumType.STRING)
    @Comment(value = "푸시 구분")
    @Column(length = 10)
    private PushKind pushKind;

    @Comment(value = "푸시 설정 여부")
    @Column(columnDefinition = "VARCHAR(1) default 'N'")
    private String pushSetYn;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    //TODO 대표앱 매핑
}
