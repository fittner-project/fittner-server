package kr.co.fittnerserver.entity.user;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.user.enums.MemberGender;
import kr.co.fittnerserver.entity.user.enums.TicketCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDate ticketStartDate;
    @Comment(value = "티켓 종료일자")
    private LocalDate ticketEndDate;
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
    private LocalDate ticketSuspendStartDate;
    @Comment(value = "티켓 일시정지 종료일자")
    private LocalDate ticketSuspendEndDate;

    @ManyToOne
    @JoinColumn(name = "trainer_product_id")
    private TrainerProduct trainerProduct;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
