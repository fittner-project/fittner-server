package kr.co.fittnerserver.entity.user;

import jakarta.persistence.*;
import kr.co.fittnerserver.dto.user.ticket.request.SuspendTicketReqDto;
import kr.co.fittnerserver.dto.user.user.request.MemberRegisterReqDto;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.user.enums.TicketCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "티켓 키값")
    private String ticketId;
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

    @Comment(value = "일시정지 사유")
    private String ticketSuspendReason;

    @ManyToOne
    @JoinColumn(name = "trainer_product_id")
    private TrainerProduct trainerProduct;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Ticket(MemberRegisterReqDto memberRegisterReqDto, TrainerProduct trainerProduct, Trainer trainer, Member member) {
        this.ticketStartDate = memberRegisterReqDto.getProductStartDate();
        this.ticketEndDate = memberRegisterReqDto.getProductEndDate();
        this.ticketCode = TicketCode.ING;
        this.trainerProduct = trainerProduct;
        this.ticketEndYn = "N";
        this.ticketDeleteYn = "N";
        this.trainer = trainer;
        this.member = member;
    }

    public void suspendTicket(String suspendReason, String suspendStartDate) {
        this.ticketCode = TicketCode.STOP;
        this.ticketSuspendReason = suspendReason;
        this.ticketSuspendStartDate = suspendStartDate;
    }

    public void againstSuspendTicket(String changeTicketEndDateStr, String suspendEndDate) {
        this.ticketCode = TicketCode.ING;
        this.ticketEndDate = changeTicketEndDateStr;
        this.ticketSuspendReason = "";
        this.ticketSuspendStartDate = "";
        this.ticketSuspendEndDate = suspendEndDate;
    }

    public void delete() {
        this.ticketDeleteYn = "Y";
    }

    public void updateSuspendDate(SuspendTicketReqDto suspendTicketReqDto) {
        this.ticketSuspendStartDate = suspendTicketReqDto.getTicketSuspendStartDate();
        this.ticketSuspendEndDate = suspendTicketReqDto.getTicketSuspendEndDate();
    }
}
