package kr.co.fittnerserver.entity.common;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.admin.Center;
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
public class Push extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "푸시 키값")
    private String pushId;

    @Comment(value = "푸시 제목")
    @Column(length = 100)
    private String pushTitle;

    @Comment(value = "푸시 내용")
    @Column(columnDefinition = "TEXT")
    private String pushContent;

    @Comment(value = "읽음 여부")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String pushReadYn;

    @Comment(value = "푸시 일자(yyyyMMdd)")
    @Column(length = 8)
    private String pushDate;

    @Comment(value = "푸시 시간(HHmmss)")
    @Column(length = 6)
    private String pushTime;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    //TODO 대표 테이블 매핑

}
