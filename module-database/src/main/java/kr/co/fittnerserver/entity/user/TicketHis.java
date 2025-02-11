package kr.co.fittnerserver.entity.user;

import jakarta.persistence.*;
import kr.co.fittnerserver.dto.user.user.request.MemberRegisterReqDto;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.user.enums.TicketCode;
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
public class TicketHis extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "티켓이력 키값")
    private String ticketHisId;
    @Comment(value = "티켓이력순번")
    private int ticketHisSeq;
    @Comment(value = "티켓이력일자")
    @Column(length = 8)
    private String ticketHisDate;
    @Comment(value = "티켓이력시")
    @Column(length = 6)
    private String ticketHisTime;
    @Comment(value = "티켓 시작일자")
    private String ticketStartDate;
    @Comment(value = "티켓 종료일자")
    private String ticketEndDate;
    @Comment(value = "티켓 삭제여부")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String ticketDeleteYn;
    @Comment(value = "티켓 종료여부")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String ticketEndYn;
    @Enumerated(EnumType.STRING)
    @Comment(value = "티켓 상태코드")
    @Column(length = 10)
    private TicketCode ticketCode;
    @Comment(value = "티켓 일시정지 시작일자")
    private String ticketSuspendStartDate;
    @Comment(value = "티켓 일시정지 종료일자")
    private String ticketSuspendEndDate;
    @Comment(value = "티켓 사용 카운트")
    @Column(length = 10)
    private int ticketUseCnt;
    @Comment(value = "원티켓ID")
    @Column(length = 38)
    private String originalTicketId;
    @Comment(value = "연장하기 티켓")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String ticketRelayYn;

    @ManyToOne
    @JoinColumn(name = "trainer_product_id")
    private TrainerProduct trainerProduct;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}
