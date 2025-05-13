package kr.co.fittnerserver.entity.user;

import jakarta.persistence.*;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationReqDto;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.admin.TrainerSettlement;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.user.enums.ReservationColor;
import kr.co.fittnerserver.entity.user.enums.ReservationPush;
import kr.co.fittnerserver.entity.user.enums.ReservationStatus;
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
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "예약 키값")
    private String reservationId;

    @Comment(value = "예약 시작일자")
    private String reservationStartDate;

    @Comment(value = "예약 종료일자")
    private String reservationEndDate;

    @Comment(value = "예약 시작시간")
    @Column(length = 4)
    private String reservationStartTime;

    @Comment(value = "예약 종료시간")
    @Column(length = 4)
    private String reservationEndTime;

    @Comment(value = "메모")
    @Column(length = 100)
    private String reservationMemo;

    @Comment(value = "색상")
    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private ReservationColor reservationColor;

    @Comment(value = "예약 삭제여부")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String reservationDeleteYn;

    @Enumerated(EnumType.STRING)
    @Comment(value = "예약 상태여부")
    @Column(length = 10)
    private ReservationStatus reservationStatus;

    @Comment(value = "트레이너 정산금액")
    @Column(length = 10)
    private String trainerSettlementAmount;

    @Comment(value = "트레이너 정산보정금액")
    @Column(length = 10)
    private String trainerSettlementChangeAmount;

    @Enumerated(EnumType.STRING)
    @Comment(value = "일정알림 푸시")
    @Column(length = 20)
    private ReservationPush reservationPush;

    @ManyToOne
    @JoinColumn(name = "trainer_settlement_id")
    private TrainerSettlement trainerSettlement;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public Reservation(ReservationReqDto reservationReqDto, Member member, Ticket ticket, Trainer trainer) {
        this.reservationStartDate = reservationReqDto.getReservationStartDate();
        this.reservationEndDate = reservationReqDto.getReservationEndDate();
        this.reservationStartTime = reservationReqDto.getReservationStartTime();
        this.reservationEndTime = reservationReqDto.getReservationEndTime();
        this.reservationMemo = reservationReqDto.getReservationMemo();
        this.reservationColor = ReservationColor.fromColorCode(reservationReqDto.getReservationColor());
        this.reservationDeleteYn = "N";
        this.reservationStatus = ReservationStatus.WAITING;
        this.reservationPush = ReservationPush.valueOf(reservationReqDto.getReservationPush());
        this.member = member;
        this.ticket = ticket;
        this.trainer = trainer;
        this.center = trainer.getCenter();
    }

    public void delete() {
        this.reservationDeleteYn = "Y";
    }
}
