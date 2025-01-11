package kr.co.fittnerserver.domain.user;

import jakarta.persistence.*;
import kr.co.fittnerserver.dto.user.user.request.MemberRegisterReqDto;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.user.Member;
import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.entity.user.TrainerProduct;
import kr.co.fittnerserver.entity.user.enums.TicketCode;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

@Data
public class TicketDto {

    private String ticketId;
    private String ticketStartDate;
    private String ticketEndDate;
    private String ticketStartEndDate;
    private String ticketDeleteYn;
    private String ticketEndYn;
    private String ticketCode;
    private String ticketSuspendStartDate;
    private String ticketSuspendEndDate;
    private String trainerProductId;
    private String trainerId;
    private String memberId;
}
