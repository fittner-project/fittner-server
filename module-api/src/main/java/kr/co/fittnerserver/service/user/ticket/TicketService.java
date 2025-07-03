package kr.co.fittnerserver.service.user.ticket;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.domain.user.RefundDto;
import kr.co.fittnerserver.domain.user.TicketDto;
import kr.co.fittnerserver.domain.user.TrainerProductDto;
import kr.co.fittnerserver.dto.user.ticket.request.*;
import kr.co.fittnerserver.dto.user.ticket.response.AssignToInfoResDto;
import kr.co.fittnerserver.dto.user.ticket.response.RefundInfoResDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketDetailResDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketListResDto;
import kr.co.fittnerserver.entity.admin.TicketAllow;
import kr.co.fittnerserver.entity.user.Member;
import kr.co.fittnerserver.entity.user.Refund;
import kr.co.fittnerserver.entity.user.Ticket;
import kr.co.fittnerserver.entity.user.enums.TicketCode;
import kr.co.fittnerserver.mapper.common.CommonMapper;
import kr.co.fittnerserver.mapper.user.reservation.ReservationMapper;
import kr.co.fittnerserver.mapper.user.ticket.TicketMapper;
import kr.co.fittnerserver.mapper.user.user.UserMapper;
import kr.co.fittnerserver.repository.user.ReservationRepository;
import kr.co.fittnerserver.repository.user.TicketAllowRepository;
import kr.co.fittnerserver.repository.user.TicketRepository;
import kr.co.fittnerserver.util.AES256Cipher;
import kr.co.fittnerserver.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    final TicketMapper ticketMapper;
    final CommonMapper commonMapper;
    final UserMapper userMapper;
    final ReservationMapper reservationMapper;
    private final TicketRepository ticketRepository;
    private final TicketAllowRepository ticketAllowRepository;
    private final ReservationRepository reservationRepository;


    public List<TicketListResDto> getTickets(String ticketCode, CustomUserDetails customUserDetails) throws Exception{
        List<TicketListResDto> r = new ArrayList<>();

        if("TOTAL".equals(ticketCode)){
            ticketCode = ""; //전체일때 조건 초기화
        }

        TicketDto param = new TicketDto();
        param.setTrainerId(customUserDetails.getTrainerId());
        param.setTicketCode(ticketCode);

        List<TicketDto> ticketDtoList = ticketMapper.selectTicketByForOption(param);

        for(TicketDto data : ticketDtoList){
            TicketListResDto ticketListResDto = new TicketListResDto();
            ticketListResDto.setTicketId(data.getTicketId());
            ticketListResDto.setTicketCode(data.getTicketCode());
            ticketListResDto.setMemberName(data.getMemberName());
            ticketListResDto.setTicketName(data.getTicketName());
            ticketListResDto.setTicketStartDate(data.getTicketStartDate());
            ticketListResDto.setTicketEndDate(data.getTicketEndDate());
            ticketListResDto.setMemberPhoneEnd(data.getMemberPhoneEnd());
            ticketListResDto.setTicketTotalCnt(data.getTicketTotalCnt());

            r.add(ticketListResDto);
        }

        return r;
    }

    public TicketDetailResDto getTicketDetail(String ticketId, CustomUserDetails customUserDetails) throws Exception{
        TicketDetailResDto r = new TicketDetailResDto();

        //이용권 정보
        TicketDto param = new TicketDto();
        param.setTicketId(ticketId);
        List<TicketDto> ticketDtoList = ticketMapper.selectTicketByForOption(param);

        if(ticketDtoList == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_TICKET.getCode(), CommonErrorCode.NOT_FOUND_TICKET.getMessage()); //티켓을 찾을 수 없습니다.
        }

        //이용권은 1개만 조회 가능
        TicketDto ticketDto = ticketDtoList.get(0);

        //이용권 정보 set
        TicketDetailResDto.TicketInfo ticketInfo = new TicketDetailResDto.TicketInfo();
        ticketInfo.setTicketCode(ticketDto.getTicketCode());
        ticketInfo.setTicketCodeName(ticketDto.getTicketCodeName());
        ticketInfo.setTicketName(ticketDto.getTicketName());
        ticketInfo.setTicketStartDate(ticketDto.getTicketStartDate());
        ticketInfo.setTicketEndDate(ticketDto.getTicketEndDate());
        ticketInfo.setTicketUseCnt(ticketDto.getTicketUseCnt());
        ticketInfo.setTicketTotalCnt(ticketDto.getTicketTotalCnt());
        ticketInfo.setTicketPrice(ticketDto.getTicketPrice());
        ticketInfo.setTicketSuspendStartDate(ticketDto.getTicketSuspendStartDate());
        ticketInfo.setTicketSuspendEndDate(ticketDto.getTicketSuspendEndDate());
        r.setTicketInfo(ticketInfo);

        //회원 정보 set
        //TODO 회원 개인정보 암호화 누락들 추가(현재 핸드폰만 되어있음)
        TicketDetailResDto.MemberInfo memberInfo = new TicketDetailResDto.MemberInfo();
        memberInfo.setMemberName(ticketDto.getMemberName());
        memberInfo.setMemberPhone(AES256Cipher.decrypt(ticketDto.getMemberPhone()));
        memberInfo.setMemberGender(ticketDto.getMemberGender());
        memberInfo.setMemberBirth(ticketDto.getMemberBirth());
        memberInfo.setMemberAddress(ticketDto.getMemberAdress());
        memberInfo.setMemberMemo(ticketDto.getMemberMemo());
        memberInfo.setMemberJoinPath(ticketDto.getMemberJoinPath());
        r.setMemberInfo(memberInfo);

        //이용권 코드에 따른 추가정보(양도,환불)
        TicketDto assign = null;
        Refund refund = null;
        if("ASSIGN_FROM".equals(ticketDto.getTicketCode())){
            assign = ticketMapper.selectTicketByTicketId(ticketDto.getOriginalTicketId(), "NORMAL");
        }else if("ASSIGN_TO".equals(ticketDto.getTicketCode())){
            assign = ticketMapper.selectTicketByTicketId(ticketDto.getTicketId(), "ORIGINAL");
        }else if("REFUND".equals(ticketDto.getTicketCode())){
            refund = ticketMapper.selectRefund(ticketDto.getTicketId());
        }

        //양도 정보 set
        if(assign != null){
            TicketDetailResDto.AssignInfo assignInfo = new TicketDetailResDto.AssignInfo();
            assignInfo.setAssignCenterName(ticketDto.getCenterName());
            assignInfo.setAssignTrainerName(ticketDto.getTrainerName());
            assignInfo.setAssignDate(ticketDto.getCreatedDate());
            assignInfo.setAssignCnt(ticketDto.getTicketTotalCnt());
            assignInfo.setAssignMemberName(ticketDto.getMemberName());
            r.setAssignInfo(assignInfo);
        }

        //환불 정보 set
        if(refund != null){
            TicketDetailResDto.RefundInfo refundInfo = new TicketDetailResDto.RefundInfo();
            refundInfo.setRefundCnt(String.valueOf(refund.getRefundCnt()));
            refundInfo.setRefundPrice(refund.getRefundPrice());
            refundInfo.setRefundDateTime(refund.getRefundDateTime());
            r.setRefundInfo(refundInfo);
        }

        return r;
    }

    public AssignToInfoResDto ticketAssignToInfo(String ticketId, String memberId, CustomUserDetails customUserDetails) throws Exception{
        AssignToInfoResDto r = new AssignToInfoResDto();

        //양도 정책 체크
        TicketDto ticketDto = assignChk(ticketId,customUserDetails.getTrainerId(), memberId);

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

    public TicketDto assignChk(String ticketId, String trainerId, String memberId) throws Exception{
        //이용권 체크
        TicketDto ticketDto = ticketMapper.selectTicketByTicketId(ticketId, "NORMAL");
        if(ticketDto == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_TICKET.getCode(), CommonErrorCode.NOT_FOUND_TICKET.getMessage()); //티켓을 찾을 수 없습니다.
        }

        //이용권 상태확인
        String[] assignCode = {"ING", "STOP"};
        boolean assignChk = Arrays.stream(assignCode).anyMatch(ticketDto.getTicketCode()::equals);
        if(!assignChk){
            throw new CommonException(CommonErrorCode.NOT_ASSIGN.getCode(), CommonErrorCode.NOT_ASSIGN.getMessage()); //양도가 불가능한 이용권입니다.
        }

        //회원 체크
        Member member = commonMapper.selectMemberByMemberId(memberId);
        if(member == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_MEMBER.getCode(), CommonErrorCode.NOT_FOUND_MEMBER.getMessage()); //회원을 찾을 수 없습니다.
        }

        //본인 양도 불가
        if(ticketDto.getMemberId().equals(memberId)){
            throw new CommonException(CommonErrorCode.NOT_ASSIGN_SELF.getCode(), CommonErrorCode.NOT_ASSIGN_SELF.getMessage()); //본인에게는 양도가 불가능합니다.
        }

        return ticketDto;
    }

    @Transactional
    public void ticketAssignToOldMember(AssignToOldMemberReqDto assignToOldMemberReqDto, CustomUserDetails customUserDetails) throws Exception{
        //양도 정보
        TicketDto assignInfo = assignChk(assignToOldMemberReqDto.getOriginalTicketId(), customUserDetails.getTrainerId(), assignToOldMemberReqDto.getMemberId());

        //상품 등록
        int trainerProductCount = Integer.parseInt(assignInfo.getTicketTotalCnt()) - Integer.parseInt(assignInfo.getTicketUseCnt());
        int ticketOneTimePrice = Integer.parseInt(assignInfo.getTicketPrice()) / Integer.parseInt(assignInfo.getTicketTotalCnt());
        int trainerProductPrice = trainerProductCount * ticketOneTimePrice;
        TrainerProductDto trainerProductDto = new TrainerProductDto();
        trainerProductDto.setTrainerProductId(commonMapper.selectUUID());
        trainerProductDto.setTrainerProductCount(String.valueOf(trainerProductCount)); //기존 금액에서 양도된 횟수만큼 계산
        trainerProductDto.setTrainerProductPrice(String.valueOf(trainerProductPrice)); //기존 금액에서 양도된 금액만큼 계산
        trainerProductDto.setTrainerProductName(assignInfo.getTicketName());
        trainerProductDto.setCenterId(assignInfo.getCenterId());
        trainerProductDto.setTrainerId(customUserDetails.getTrainerId());
        trainerProductDto.setMemberId(assignToOldMemberReqDto.getMemberId());

        ticketMapper.insertTrainerProduct(trainerProductDto);

        //이용권 등록
        TicketDto param = new TicketDto();
        param.setMemberId(assignToOldMemberReqDto.getMemberId());
        param.setTicketEndDate(assignInfo.getTicketEndDate());
        param.setTicketId(commonMapper.selectUUID());
        param.setTicketStartDate(assignInfo.getTicketStartDate());
        param.setTrainerId(customUserDetails.getTrainerId());
        param.setTicketUseCnt("0");
        param.setOriginalTicketId(assignToOldMemberReqDto.getOriginalTicketId());
        param.setTicketCode("ASSIGN_FROM");
        param.setTicketRelayYn("Y"); //TODO 수업이 없는 회원일 경우 고민필요(최초1회 등록후 바로 환불이면 연장하기퍼센트 의미가 없음)
        param.setTrainerProductId(trainerProductDto.getTrainerProductId());

        ticketMapper.insertTicket(param);

        //양도한 이용권 상태 업데이트
        //ticketMapper.updateTicketForTicketCode(assignToOldMemberReqDto.getOriginalTicketId(), customUserDetails.getTrainerId(), "ASSIGN_TO");
    }

    @Transactional
    public void ticketAssignToNewMember(AssignToNewMemberReqDto assignToNewMemberReqDto, CustomUserDetails customUserDetails) throws Exception{
        //이용권 기간 체크
        Util.ticketStartEndDateChk(assignToNewMemberReqDto.getProductStartDate(), assignToNewMemberReqDto.getProductEndDate(), true);

        //회원등록
        String memberPhoneEnd = assignToNewMemberReqDto.getMemberPhone().substring(8);
        String memberId = commonMapper.selectUUID();
        ticketMapper.insertMember(assignToNewMemberReqDto, memberPhoneEnd, customUserDetails.getTrainerId(), memberId);

        //상품 등록
        TrainerProductDto trainerProductDto = new TrainerProductDto();
        trainerProductDto.setTrainerProductId(commonMapper.selectUUID());
        trainerProductDto.setTrainerProductCount(assignToNewMemberReqDto.getProductCount());
        trainerProductDto.setTrainerProductPrice(assignToNewMemberReqDto.getProductPrice());
        trainerProductDto.setTrainerProductName(assignToNewMemberReqDto.getProductName());
        trainerProductDto.setCenterId(assignToNewMemberReqDto.getCenterId());
        trainerProductDto.setTrainerId(assignToNewMemberReqDto.getTrainerId());
        trainerProductDto.setMemberId(memberId);

        //이용권등록
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTrainerId(assignToNewMemberReqDto.getTrainerId());
        ticketDto.setMemberId(memberId);
        ticketDto.setTicketEndDate(assignToNewMemberReqDto.getProductEndDate());
        ticketDto.setTicketId(commonMapper.selectUUID());
        ticketDto.setTicketStartDate(assignToNewMemberReqDto.getProductStartDate());
        ticketDto.setTrainerId(assignToNewMemberReqDto.getTrainerId());
        ticketDto.setTrainerProductId(trainerProductDto.getTrainerProductId()); //신규로 만든 이용권
        ticketDto.setTicketCode("ASSIGN_FROM");
        ticketDto.setOriginalTicketId(assignToNewMemberReqDto.getOriginalTicketId());
        ticketDto.setTicketUseCnt("0");
        ticketDto.setTicketRelayYn("N");
        ticketMapper.insertTicket(ticketDto);

        //양도한 이용권 상태 업데이트
        ticketMapper.updateTicketForTicketCode(assignToNewMemberReqDto.getOriginalTicketId(), customUserDetails.getTrainerId(), "ASSIGN_TO");
    }

    @Transactional
    public void ticketPlus(PlusReqDto plusReqDto, CustomUserDetails customUserDetails) throws Exception{
        //이용권 기간 체크
        Util.ticketStartEndDateChk(plusReqDto.getProductStartDate(), plusReqDto.getProductEndDate(), true);

        //트레이너의 내 회원인지 체크
        List<Member> memberList = commonMapper.selectMemberByCenterIdAndTrainerId(plusReqDto.getCenterId(), customUserDetails.getTrainerId());
        if(memberList.size() == 0){
            throw new CommonException(CommonErrorCode.NOT_ALLOW_TRAINER_MEMBER.getCode(), CommonErrorCode.NOT_ALLOW_TRAINER_MEMBER.getMessage()); //트레이너에 소속된 회원이 아닙니다.
        }

        //상품 등록
        TrainerProductDto trainerProductDto = new TrainerProductDto();
        trainerProductDto.setTrainerProductId(commonMapper.selectUUID());
        trainerProductDto.setTrainerProductCount(plusReqDto.getProductCount());
        trainerProductDto.setTrainerProductPrice(plusReqDto.getProductPrice());
        trainerProductDto.setTrainerProductName(plusReqDto.getProductName());
        trainerProductDto.setCenterId(plusReqDto.getCenterId());
        trainerProductDto.setTrainerId(customUserDetails.getTrainerId());
        trainerProductDto.setMemberId(plusReqDto.getMemberId());

        ticketMapper.insertTrainerProduct(trainerProductDto);

        //이용권 등록
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTrainerId(customUserDetails.getTrainerId());
        ticketDto.setMemberId(plusReqDto.getMemberId());
        ticketDto.setTicketEndDate(plusReqDto.getProductEndDate());
        ticketDto.setTicketId(commonMapper.selectUUID());
        ticketDto.setTicketStartDate(plusReqDto.getProductStartDate());
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

        //이용권 확인
        TicketDto ticketDto = ticketMapper.selectTicketByTicketId(ticketId,"NORMAL");
        if(ticketDto == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_TICKET.getCode(), CommonErrorCode.NOT_FOUND_TICKET.getMessage()); //티켓을 찾을 수 없습니다.
        }

        //이용권 상태확인 TODO before 코드 사용시 추가
        String[] refundCode = {"ING", "STOP"};
        boolean refundCnk = Arrays.stream(refundCode).anyMatch(ticketDto.getTicketCode()::equals);
        if(!refundCnk){
            throw new CommonException(CommonErrorCode.NOT_REFUND.getCode(), CommonErrorCode.NOT_REFUND.getMessage()); //환불이 불가능한 이용권입니다.
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
        r.setTicketRemainingCnt(String.valueOf(Integer.parseInt(ticketDto.getTicketTotalCnt()) - Integer.parseInt(ticketDto.getTicketUseCnt())));

        return r;
    }

    @Transactional
    public void ticketRefund(RefundReqDto refundReqDto, CustomUserDetails customUserDetails) throws Exception{
        //환불 정보 조회
        RefundInfoResDto refundInfo = ticketRefundInfo(refundReqDto.getTicketId(), customUserDetails);

        //이용권 정보 조회
        TicketDto ticketDto = ticketMapper.selectTicketByTicketId(refundReqDto.getTicketId(),"NORMAL");


        Ticket ticket = ticketRepository.findById(ticketDto.getTicketId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TICKET.getCode(), CommonErrorCode.NOT_FOUND_TICKET.getMessage()));

        Boolean reservationCheck = reservationRepository.existsByTicket(ticket);

        if(reservationCheck) {
           throw new CommonException(CommonErrorCode.NOT_REQUEST_REFUND.getCode(), CommonErrorCode.NOT_REQUEST_REFUND.getMessage());
        }


        //관리자에게 환불 요청
        ticketAllowRepository.save(new TicketAllow("N",TicketCode.REFUND,ticket,ticket.getTrainer()));

        //환불 저장
        RefundDto refundDto = new RefundDto();
        refundDto.setRefundCnt(refundInfo.getTicketRemainingCnt());
        refundDto.setRefundId(commonMapper.selectUUID());
        refundDto.setRefundPrice(refundInfo.getRefundPrice());
        refundDto.setRefundDateTime(Util.getFormattedToday("yyyyMMdd"));
        refundDto.setCenterId(ticketDto.getCenterId());
        refundDto.setMemberId(ticketDto.getMemberId());
        refundDto.setTicketId(ticketDto.getTicketId());
        refundDto.setTrainerId(ticketDto.getTrainerId());

        ticketMapper.insertRefund(refundDto);


        //TODO 관리자에 들어갈 프로세스
        //이용권 업데잍
        //ticketMapper.updateTicketForTicketCode(ticketDto.getTicketId(), customUserDetails.getTrainerId(), "REFUND");
    }

    @Transactional
    public void suspendMemberTicket(SuspendTicketReqDto suspendTicketReqDto) {
        Ticket ticketInfo = ticketRepository.findById(suspendTicketReqDto.getTicketId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TICKET.getCode(), CommonErrorCode.NOT_FOUND_TICKET.getMessage()));

        //이미 일시정지된 티켓이면 예외처리
        if(ticketInfo.getTicketCode().equals(TicketCode.STOP)){
            throw new CommonException(CommonErrorCode.ALREADY_SUSPEND_TICKET.getCode(), CommonErrorCode.ALREADY_SUSPEND_TICKET.getMessage());
        }

        //티켓 일시정지 기간 등록
        ticketInfo.updateSuspendDate(suspendTicketReqDto);

        ticketAllowRepository.save(new TicketAllow("N",TicketCode.STOP,ticketInfo,ticketInfo.getTrainer()));

        //TODO 관리자에서 처리
        //티켓 일시정지 상태로 변경
        //ticketInfo.suspendTicket(suspendTicketReqDto.getSuspendReason(), String.valueOf(LocalDate.now()));
    }

    @Transactional
    public void againstSuspendMemberTicket(String ticketId) {
        Ticket ticketInfo = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TICKET.getCode(), CommonErrorCode.NOT_FOUND_TICKET.getMessage()));

        //이미 사용중인 티켓이면 예외처리
        if(ticketInfo.getTicketCode().equals(TicketCode.ING)){
            throw new CommonException(CommonErrorCode.ALREADY_ING_TICKET.getCode(), CommonErrorCode.ALREADY_ING_TICKET.getMessage());
        }

        ticketAllowRepository.save(new TicketAllow("N", TicketCode.ING, ticketInfo, ticketInfo.getTrainer()));

        /*LocalDate suspendStartDate = LocalDate.parse(ticketInfo.getTicketSuspendStartDate(),DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate currentDate = LocalDate.now();

        //늘려야 할 일수
        long daysBetween = ChronoUnit.DAYS.between(suspendStartDate, currentDate);
        LocalDate ticketEndDate = LocalDate.parse(ticketInfo.getTicketEndDate(),DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate changeTicketEndDate = ticketEndDate.plusDays(daysBetween);

        String changeTicketEndDateStr = changeTicketEndDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        //티켓 일시정지 상태 해제
        ticketInfo.againstSuspendTicket(changeTicketEndDateStr, currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));*/
    }
}
