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
public class Refund extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "환불 키값")
    private String refundId;

    @Comment(value = "환불횟수")
    @Column(length = 5)
    private int refundCnt;

    @Comment(value = "환불금액")
    @Column(length = 10)
    private String refundPrice;

    @Comment(value = "환불일시")
    @Column(length = 14)
    private String refundDateTime;

    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

}
