package kr.co.fittnerserver.mapper.user.ticket;

import kr.co.fittnerserver.domain.user.TicketDto;
import kr.co.fittnerserver.dto.user.ticket.request.TicketListReqDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketDetailResDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketListResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface TicketMapper {

    List<TicketListResDto> getTickets(@Param("ticketListReqDto")TicketListReqDto ticketListReqDto, @Param("currentPageNo") int currentPageNo, @Param("trainerId") String trainerId);

    TicketDetailResDto selectTicketByTicketId(String ticketId, String trainerId);

    TicketDetailResDto selectTicketByTicketIdForAssign(String originalTicketId);
}
