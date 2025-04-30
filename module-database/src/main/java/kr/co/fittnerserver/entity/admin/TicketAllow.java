package kr.co.fittnerserver.entity.admin;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.user.Ticket;
import kr.co.fittnerserver.entity.user.Trainer;
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
public class TicketAllow extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "티켓 승인 키값")
    private String ticketAllowId;
    @Comment(value = "티켓 승인 완료 유무")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String ticketAllowCompleteYn;
    @Enumerated(EnumType.STRING)
    @Comment(value = "티켓 상태코드")
    @Column(length = 10)
    private TicketCode ticketCode;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    public TicketAllow(String ticketAllowCompleteYn, TicketCode ticketCode, Ticket ticket, Trainer trainer) {
        this.ticketAllowCompleteYn = ticketAllowCompleteYn;
        this.ticketCode = ticketCode;
        this.ticket = ticket;
        this.trainer = trainer;
    }

}
