package kr.co.fittnerserver.mapper.user.ticket;

import kr.co.fittnerserver.domain.user.RefundDto;
import kr.co.fittnerserver.domain.user.TicketDto;
import kr.co.fittnerserver.domain.user.TrainerProductDto;
import kr.co.fittnerserver.dto.user.ticket.request.AssignToNewMemberReqDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketListResDto;
import kr.co.fittnerserver.entity.user.Refund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface TicketMapper {

    List<TicketDto> selectTicketByForOption(TicketDto ticketDto);

    TicketDto selectTicketByTicketId(String ticketId, String gubn);

    Refund selectRefund(String ticketId);

    void insertTicket(TicketDto ticketDto);

    void updateTicketForTicketCode(String ticketId, String trainerId, String ticketCode);

    void insertMember(@Param("AssignToNewMemberReqDto") AssignToNewMemberReqDto assignToNewMemberReqDto, @Param("memberPhoneEnd") String memberPhoneEnd, @Param("trainerId") String trainerId, @Param("memberId") String memberId);

    void insertTrainerProduct(TrainerProductDto trainerProductDto);

    void updateTicketForUseCnt(String ticketId);

    void insertRefund(RefundDto refundDto);

    TicketDto selectTicketByMemberIdForNowTicket(String memberId, String reservationStartDate);

}
