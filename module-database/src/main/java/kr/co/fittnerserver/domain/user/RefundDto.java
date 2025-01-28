package kr.co.fittnerserver.domain.user;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.user.Member;
import kr.co.fittnerserver.entity.user.Ticket;
import kr.co.fittnerserver.entity.user.Trainer;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

@Data
public class RefundDto {

    private String refundId;
    private String refundCnt;
    private String refundPrice;
    private String refundDateTime;
    private String ticketId;
    private String memberId;
    private String trainerId;
    private String centerId;

}
