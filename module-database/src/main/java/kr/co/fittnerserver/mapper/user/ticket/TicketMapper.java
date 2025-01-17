package kr.co.fittnerserver.mapper.user.ticket;

import kr.co.fittnerserver.domain.user.TicketDto;
import kr.co.fittnerserver.dto.user.assign.request.AssignToNewMemberReqDto;
import kr.co.fittnerserver.dto.user.assign.request.AssignToOldMemberReqDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketListResDto;
import kr.co.fittnerserver.entity.user.Refund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface TicketMapper {

    List<TicketListResDto> getTickets(String ticketStatus, int currentPageNo, String trainerId);

    TicketDto selectTicketByTicketId(String ticketId, String gubn);

    Refund selectRefund(String ticketId);

    void insertTicket(TicketDto ticketDto);

    void updateTicketForAssign(String ticketId, String trainerId);

    void insertMember(@Param("AssignToNewMemberReqDto") AssignToNewMemberReqDto assignToNewMemberReqDto, @Param("memberPhoneEnd") String memberPhoneEnd, @Param("trainerId") String trainerId, @Param("memberId") String memberId);

}
