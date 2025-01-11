package kr.co.fittnerserver.service.user.ticket;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.ticket.request.TicketListReqDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketListResDto;
import kr.co.fittnerserver.mapper.user.ticket.TicketMapper;
import kr.co.fittnerserver.results.FittnerPageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TIcketService {

    final TicketMapper ticketMapper;

    public List<TicketListResDto> getTickets(TicketListReqDto ticketListReqDto, FittnerPageable pageable, CustomUserDetails customUserDetails){
        return ticketMapper.getTickets(ticketListReqDto,pageable.getCurrentPageNo(),customUserDetails.getTrainerId());
    }
}
