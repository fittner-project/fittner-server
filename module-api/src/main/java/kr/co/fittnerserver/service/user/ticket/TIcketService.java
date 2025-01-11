package kr.co.fittnerserver.service.user.ticket;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.domain.user.TicketDto;
import kr.co.fittnerserver.dto.user.ticket.request.TicketListReqDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketDetailResDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketListResDto;
import kr.co.fittnerserver.mapper.user.ticket.TicketMapper;
import kr.co.fittnerserver.results.FittnerPageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    public TicketDetailResDto getTicketDetail(String ticketId, CustomUserDetails customUserDetails){
        TicketDetailResDto r = new TicketDetailResDto();

        //기본정보
        TicketDetailResDto basicInfo = ticketMapper.selectTicketByTicketId(ticketId, customUserDetails.getTrainerId());

        if(basicInfo == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_TICKET.getCode(), CommonErrorCode.NOT_FOUND_TICKET.getMessage());
        }

        //양도구분
        String assignTicketId = "";
        if("ASSIGN_FROM".equals(basicInfo.getTicketCode())){
            assignTicketId = basicInfo.getOriginalTicketId();
        }else if("ASSIGN_TO".equals(basicInfo.getTicketCode())){
            assignTicketId = basicInfo.getTicketId();
        }

        //양도 정보 조회
        if(!StringUtils.isEmpty(assignTicketId)){
            TicketDetailResDto ticketDto = ticketMapper.selectTicketByTicketIdForAssign(basicInfo.getOriginalTicketId());
            r.setOriginalTicketId(basicInfo.getOriginalTicketId());
            r.setAssignCenterName(ticketDto.getAssignCenterName());
            r.setAssignTrainerName(ticketDto.getAssignTrainerName());
            r.setAssignDate(ticketDto.getAssignDate()); //TODO 날짜 필드 추가
            r.setAssignCnt(ticketDto.getAssignCnt()); //TODO 매번 카운트할지 필드 추가할지 고민 필요
            r.setAssignName(ticketDto.getAssignName());
        }

        return r;
    }
}
