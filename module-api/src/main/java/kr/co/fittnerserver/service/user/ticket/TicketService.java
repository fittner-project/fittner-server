package kr.co.fittnerserver.service.user.ticket;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.domain.user.TicketDto;
import kr.co.fittnerserver.domain.user.TrainerDto;
import kr.co.fittnerserver.domain.user.TrainerProductDto;
import kr.co.fittnerserver.dto.user.ticket.request.AssignToNewMemberReqDto;
import kr.co.fittnerserver.dto.user.ticket.request.AssignToOldMemberReqDto;
import kr.co.fittnerserver.dto.user.ticket.request.RelayReqDto;
import kr.co.fittnerserver.dto.user.ticket.response.AssignToInfoResDto;
import kr.co.fittnerserver.dto.user.ticket.response.RefundInfoResDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketDetailResDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketListResDto;
import kr.co.fittnerserver.entity.user.Member;
import kr.co.fittnerserver.entity.user.Refund;
import kr.co.fittnerserver.mapper.common.CommonMapper;
import kr.co.fittnerserver.mapper.user.reservation.ReservationMapper;
import kr.co.fittnerserver.mapper.user.ticket.TicketMapper;
import kr.co.fittnerserver.mapper.user.user.UserMapper;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    final TicketMapper ticketMapper;
    final CommonMapper commonMapper;
    final UserMapper userMapper;
    final ReservationMapper reservationMapper;

    public List<TicketListResDto> getTickets(String ticketStatus, FittnerPageable pageable, CustomUserDetails customUserDetails){
        return ticketMapper.getTickets(ticketStatus,pageable.getCurrentPageNo(),customUserDetails.getTrainerId());
    }

    public TicketDetailResDto getTicketDetail(String ticketId, CustomUserDetails customUserDetails){
        TicketDetailResDto r = new TicketDetailResDto();

        //이용권 정보
        TicketDto ticketDto = ticketMapper.selectTicketByTicketId(ticketId, "NORMAL");

        if(ticketDto == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_TICKET.getCode(), CommonErrorCode.NOT_FOUND_TICKET.getMessage());
        }

        //티켓 정보 set
        r.setTicketCodeName(ticketDto.getTicketCodeName());
        r.setTicketName(ticketDto.getTicketName());
        r.setTicketStartDate(ticketDto.getTicketStartDate());
        r.setTicketEndDate(ticketDto.getTicketEndDate());
        r.setTicketUseCnt(ticketDto.getTicketUseCnt());
        r.setTicketTotalCnt(ticketDto.getTicketTotalCnt());
        r.setTicketPrice(ticketDto.getTicketPrice());

        //회원 정보 set
        r.setMemeberName(ticketDto.getMemberName());
        r.setMemberPhone(ticketDto.getMemberPhone());
        r.setMemberGender(ticketDto.getMemberGender());
        r.setMemberBirth(ticketDto.getMemberBirth());
        r.setMemberAddress(ticketDto.getMemberAdress());
        r.setMemberMomo(ticketDto.getMemberMemo());
        r.setMemberJoinPath(ticketDto.getMemberJoinPath());

        //이용권 구분
        TicketDto ticketDtoAssign = null;
        Refund refund = null;
        if("ASSIGN_FROM".equals(ticketDto.getTicketCode())){
            ticketDtoAssign = ticketMapper.selectTicketByTicketId(ticketDto.getOriginalTicketId(), "NORMAL");
        }else if("ASSIGN_TO".equals(ticketDto.getTicketCode())){
            ticketDtoAssign = ticketMapper.selectTicketByTicketId(ticketDto.getTicketId(), "ORIGINAL");
        }else if("REFUND".equals(ticketDto.getTicketCode())){
            refund = ticketMapper.selectRefund(ticketDto.getTicketId());
        }

        //양도 정보 set
        if(ticketDtoAssign != null){
            r.setAssignCenterName(ticketDto.getCenterName());
            r.setAssignTrainerName(ticketDto.getTrainerName());
            r.setAssignDate(ticketDto.getCreatedDate());
            r.setAssignCnt(ticketDto.getTicketTotalCnt());
            r.setAssignMemberName(ticketDto.getMemberName());
        }

        //환불 정보 set
        if(refund != null){
            r.setRefundCnt(String.valueOf(refund.getRefundCnt()));
            r.setRefundPrice(refund.getRefundPrice());
            r.setRefundDateTime(refund.getRefundDateTime());
        }

        return r;
    }

    public AssignToInfoResDto ticketAssignToInfo(String ticketId, String memberId, CustomUserDetails customUserDetails) throws Exception{
        AssignToInfoResDto r = new AssignToInfoResDto();

        //이용권 체크
        TicketDto ticketDto = ticketMapper.selectTicketByTicketId(ticketId, customUserDetails.getTrainerId());
        if(ticketDto == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_TICKET.getCode(), CommonErrorCode.NOT_FOUND_TICKET.getMessage()); //티켓을 찾을 수 없습니다.
        }

        //회원 체크
        Member member = commonMapper.selectMemberByMemberId(memberId);
        if(member == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_MEMBER.getCode(), CommonErrorCode.NOT_FOUND_MEMBER.getMessage()); //회원을 찾을 수 없습니다.
        }

        //동일회원 양도 불가
        if(ticketDto.getMemberId().equals(memberId)){
            throw new CommonException(CommonErrorCode.NOT_ASSIGN_SAME_MEMBER.getCode(), CommonErrorCode.NOT_ASSIGN_SAME_MEMBER.getMessage()); //동일회원에게 양도가 불가능합니다.
        }

        //양도 티켓정보
        r.setTicketId(ticketDto.getTicketId());
        r.setTicketName(ticketDto.getTicketName());
        r.setTicketStartDate(ticketDto.getTicketStartDate());
        r.setTicketEndDate(ticketDto.getTicketEndDate());
        r.setRemainingCnt(String.valueOf(Integer.parseInt(ticketDto.getTicketTotalCnt()) - Integer.parseInt(ticketDto.getTicketUseCnt())));
        r.setTotalCnt(ticketDto.getTicketTotalCnt());
        r.setMemberName(ticketDto.getMemberName());
        r.setMemberPhoneEnd(ticketDto.getMemberPhoneEnd());

        return r;
    }

    public void ticketAssignToOldMember(AssignToOldMemberReqDto assignToOldMemberReqDto, CustomUserDetails customUserDetails) throws Exception{
        //이용권 등록
        TicketDto ticketDto = ticketMapper.selectTicketByTicketId(assignToOldMemberReqDto.getOriginalTicketId(),"NORMAL");
        ticketDto.setMemberId(assignToOldMemberReqDto.getMemberId());
        ticketDto.setTicketUseCnt("0");
        ticketDto.setTicketId(commonMapper.selectUUID());
        ticketDto.setOriginalTicketId(assignToOldMemberReqDto.getOriginalTicketId());
        ticketDto.setTicketCode("ASSIGN_FROM");

        ticketMapper.insertTicket(ticketDto);

        //양도한 이용권 상태 업데이트
        ticketMapper.updateTicketForAssign(assignToOldMemberReqDto.getOriginalTicketId(), customUserDetails.getTrainerId());
    }

    @Transactional
    public void ticketAssignToNewMember(AssignToNewMemberReqDto assignToNewMemberReqDto, CustomUserDetails customUserDetails) throws Exception{
        //이용권 기간 체크
        Util.ticketStartEndDateChk(assignToNewMemberReqDto.getProductStartDate(), assignToNewMemberReqDto.getProductEndDate(), true);

        //회원등록
        String memberPhoneEnd = assignToNewMemberReqDto.getMemberPhone().substring(8);
        String memberId = commonMapper.selectUUID();
        ticketMapper.insertMember(assignToNewMemberReqDto, memberPhoneEnd, customUserDetails.getTrainerId(), memberId);

        //이용권등록
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTrainerId(assignToNewMemberReqDto.getTrainerId());
        ticketDto.setMemberId(memberId);
        ticketDto.setTicketEndDate(assignToNewMemberReqDto.getProductEndDate());
        ticketDto.setTicketId(commonMapper.selectUUID());
        ticketDto.setTicketStartDate(assignToNewMemberReqDto.getProductStartDate());
        ticketDto.setTrainerId(assignToNewMemberReqDto.getTrainerId());
        ticketDto.setTrainerProductId(assignToNewMemberReqDto.getTrainerProductId());
        ticketDto.setTicketCode("ASSIGN_FROM");
        ticketDto.setOriginalTicketId(assignToNewMemberReqDto.getOriginalTicketId());
        ticketDto.setTicketUseCnt("0");
        ticketMapper.insertTicket(ticketDto);

        //양도한 이용권 상태 업데이트
        ticketMapper.updateTicketForAssign(assignToNewMemberReqDto.getOriginalTicketId(), customUserDetails.getTrainerId());
    }

    @Transactional
    public void ticketRelay(RelayReqDto relayReqDto, CustomUserDetails customUserDetails) throws Exception{
        //이용권 기간 체크
        Util.ticketStartEndDateChk(relayReqDto.getProductStartDate(), relayReqDto.getProductEndDate(), true);

        //센터정보
        TrainerDto trainer = userMapper.selectTrainerByTrainerId(customUserDetails.getTrainerId());

        //상품 등록
        TrainerProductDto trainerProductDto = new TrainerProductDto();
        trainerProductDto.setTrainerProductId(commonMapper.selectUUID());
        trainerProductDto.setTrainerProductCount(relayReqDto.getProductCount());
        trainerProductDto.setTrainerProductPrice(relayReqDto.getProductPrice());
        trainerProductDto.setTrainerProductName(relayReqDto.getProductName());
        trainerProductDto.setCenterId(trainer.getCenterId());
        trainerProductDto.setTrainerId(customUserDetails.getTrainerId());

        ticketMapper.insertTrainerProduct(trainerProductDto);

        //이용권 등록
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTrainerId(customUserDetails.getTrainerId());
        ticketDto.setMemberId(relayReqDto.getMemberId());
        ticketDto.setTicketEndDate(relayReqDto.getProductEndDate());
        ticketDto.setTicketId(commonMapper.selectUUID());
        ticketDto.setTicketStartDate(relayReqDto.getProductStartDate());
        ticketDto.setTrainerProductId(trainerProductDto.getTrainerProductId());
        ticketDto.setTicketCode("ING"); //TODO 이용전으로 할지 고민 필요
        ticketDto.setTicketUseCnt("0");
        ticketDto.setTicketRelayYn("Y");

        ticketMapper.insertTicket(ticketDto);
    }

    public RefundInfoResDto ticketRefundInfo(String ticketId, CustomUserDetails customUserDetails) throws Exception{
        RefundInfoResDto r = new RefundInfoResDto();

        //예약된 수업 있는지 확인
        int reservatinoChk = reservationMapper.selectReservationForCount(ticketId);
        if(reservatinoChk > 0){
            throw new CommonException(CommonErrorCode.RESERVATION_TICKET.getCode(), CommonErrorCode.RESERVATION_TICKET.getMessage()); //수업 예정인 이용권입니다.
        }

        TicketDto ticketDto = ticketMapper.selectTicketByTicketId(ticketId,"NORMAL");
        if(ticketDto == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_TICKET.getCode(), CommonErrorCode.NOT_FOUND_TICKET.getMessage()); //티켓을 찾을 수 없습니다.
        }

        //이용권 사용금액
        int ticketOneTimePrice = Integer.parseInt(ticketDto.getTicketPrice()) / Integer.parseInt(ticketDto.getTicketTotalCnt());
        int tikcetUsePrice = ticketOneTimePrice * Integer.parseInt(ticketDto.getTicketUseCnt());

        r.setTicketId(ticketDto.getTicketId());
        r.setMemberName(ticketDto.getMemberName());
        r.setTicketName(ticketDto.getTicketName());
        r.setTicketStartDate(ticketDto.getTicketStartDate());
        r.setTicketEndDate(ticketDto.getTicketEndDate());
        r.setTicketTotalCnt(ticketDto.getTicketTotalCnt());
        r.setTicketPrice(ticketDto.getTicketPrice());
        r.setTicketUsePrice(String.valueOf(tikcetUsePrice));
        r.setRefundPrice(String.valueOf(Integer.parseInt(ticketDto.getTicketPrice()) - tikcetUsePrice));

        return r;
    }
}
