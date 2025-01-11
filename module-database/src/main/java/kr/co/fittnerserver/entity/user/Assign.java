package kr.co.fittnerserver.entity.user;


import jakarta.persistence.*;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.user.enums.AssignCode;
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
public class Assign extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "양도 키값")
    private String assignId;

    @Comment(value = "양도구분")
    @Column(length = 4)
    @Enumerated(EnumType.STRING)
    private AssignCode assignCode;

    @Comment(value = "양도받은 회원 키값")
    @Column(length = 38)
    private String assignToMemberId;

    @Comment(value = "양도한 회원 키값")
    @Column(length = 38)
    private String assignFromMemberId;

    @Comment(value = "양도일시")
    @Column(length = 14)
    private String assignDateTime;

    @Comment(value = "양도횟수")
    @Column(length = 14)
    private String assignCnt;

    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;


}
