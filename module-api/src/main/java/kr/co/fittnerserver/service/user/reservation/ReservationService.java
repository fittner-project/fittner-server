package kr.co.fittnerserver.service.user.reservation;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.domain.user.ReservationDto;
import kr.co.fittnerserver.domain.user.TicketDto;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationDeleteReqDto;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationReqDto;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationSearchDto;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationUpdateReqDto;
import kr.co.fittnerserver.dto.user.reservation.response.GroupedReservationMemberDto;
import kr.co.fittnerserver.dto.user.reservation.response.ReservationColorResDto;
import kr.co.fittnerserver.dto.user.reservation.response.ReservationDetailResDto;
import kr.co.fittnerserver.dto.user.reservation.response.ReservationMemberResDto;
import kr.co.fittnerserver.entity.admin.TrainerSettlement;
import kr.co.fittnerserver.entity.admin.enums.TrainerSettlementCode;
import kr.co.fittnerserver.entity.user.Member;
import kr.co.fittnerserver.entity.user.Reservation;
import kr.co.fittnerserver.entity.user.Ticket;
import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.entity.user.enums.ReservationColor;
import kr.co.fittnerserver.entity.user.enums.ReservationStatus;
import kr.co.fittnerserver.mapper.user.reservation.ReservationMapper;
import kr.co.fittnerserver.mapper.user.ticket.TicketMapper;
import kr.co.fittnerserver.repository.TrainerSettlementRepository;
import kr.co.fittnerserver.repository.common.CenterRepository;
import kr.co.fittnerserver.repository.user.MemberRepository;
import kr.co.fittnerserver.repository.user.ReservationRepository;
import kr.co.fittnerserver.repository.user.TicketRepository;
import kr.co.fittnerserver.repository.user.TrainerRepository;
import kr.co.fittnerserver.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TrainerSettlementRepository trainerSettlementRepository;
    private final MemberRepository memberRepository;
    private final TicketRepository ticketRepository;
    private final TrainerRepository trainerRepository;
    private final TicketMapper ticketMapper;
    private final ReservationMapper reservationMapper;

    public ReservationColorResDto getColors() {
        return new ReservationColorResDto(ReservationColor.getColorCodes());
    }

    @Transactional
    public void reservation(ReservationReqDto reservationReqDto, CustomUserDetails customUserDetails) {
        reservationTimeValidation(reservationReqDto);


        Member member = memberRepository.findById(reservationReqDto.getMemberId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_MEMBER.getCode(), CommonErrorCode.NOT_FOUND_MEMBER.getMessage()));

        //현재 이용중인 or 이용될 티켓 조회
        String ticketId = "";
        TicketDto ticketDto = ticketMapper.selectTicketByMemberIdForNowTicket(reservationReqDto.getMemberId(), reservationReqDto.getReservationStartDate());
        if (ticketDto == null) {
            throw new CommonException(CommonErrorCode.NOT_ING_TICKET.getCode(), CommonErrorCode.NOT_ING_TICKET.getMessage()); //수업예약일에 이용중인 이용권 기간이 없습니다.
        }
        ticketId = ticketDto.getTicketId();

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TICKET.getCode(), CommonErrorCode.NOT_FOUND_TICKET.getMessage()));

        Trainer trainer = trainerRepository.findById(customUserDetails.getTrainerId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()));

        TrainerSettlement trainerSettlement = trainerSettlementRepository.findByTrainerAndTrainerSettlementCode(trainer, TrainerSettlementCode.NEW)
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER_SETTLE.getCode(), CommonErrorCode.NOT_FOUND_TRAINER_SETTLE.getMessage()));

        reservationRepository.save(new Reservation(reservationReqDto, member, ticket, trainer, trainerSettlement));
    }

    private void reservationTimeValidation(ReservationReqDto dto) {
        String startDateTimeStr = dto.getReservationStartDate() + dto.getReservationStartTime();
        String endDateTimeStr = dto.getReservationEndDate() + dto.getReservationEndTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr, formatter);

        // 비교 및 예외 처리
        if (!startDateTime.isBefore(endDateTime)) {
            // start >= end 인 경우 (같거나 늦으면)
            throw new CommonException(CommonErrorCode.NOT_RESERVATION_ADD.getCode(), CommonErrorCode.NOT_RESERVATION_ADD.getMessage()
            );
        }

    }

    @Transactional(readOnly = true)
    public List<GroupedReservationMemberDto> getReservations(ReservationSearchDto reservationSearchDto, CustomUserDetails customUserDetails) {
        List<ReservationMemberResDto> reservationMemberDataList = reservationRepository.getReservationMemberDataList(reservationSearchDto.getReservationStartDate(), reservationSearchDto.getReservationEndDate(), customUserDetails.getTrainerId());
        // 데이터 그룹화
        return reservationMemberDataList.stream()
                .collect(Collectors.groupingBy(ReservationMemberResDto::getLastTwoDigits))
                .entrySet().stream()
                .map(entry -> new GroupedReservationMemberDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    //수업 디테일 조회
    public ReservationDetailResDto getReservationDetail(String reservationId, CustomUserDetails customUserDetails) throws Exception{

        //수정될 예약 조회
        ReservationDto reservationDto = reservationMapper.selectReservationByReservationId(reservationId);
        if(reservationDto == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_RESERVATION.getCode(), CommonErrorCode.NOT_FOUND_RESERVATION.getMessage()); //수업 정보를 찾을 수 없습니다.
        }

        //본인 수업만 조회 가능
        if(!reservationDto.getTrainerId().equals(customUserDetails.getTrainerId())){
            throw new CommonException(CommonErrorCode.NOT_MATCH_TRAINER.getCode(), CommonErrorCode.NOT_MATCH_TRAINER.getMessage()); //트레이너 정보가 일치하지 않습니다.
        }

        ReservationDetailResDto reservationDetailResDto = new ReservationDetailResDto();
        reservationDetailResDto.setReservationId(reservationDto.getReservationId());
        reservationDetailResDto.setReservationStartDate(reservationDto.getReservationStartDate());
        reservationDetailResDto.setReservationStartTime(reservationDto.getReservationStartTime());
        reservationDetailResDto.setReservationEndDate(reservationDto.getReservationEndDate());
        reservationDetailResDto.setReservationEndTime(reservationDto.getReservationEndTime());
        reservationDetailResDto.setReservationColor(reservationDto.getReservationColor());
        reservationDetailResDto.setReservationPush(reservationDto.getReservationPush());
        reservationDetailResDto.setReservationMemo(reservationDto.getReservationMemo());

        return reservationDetailResDto;
    }

    //수업 변경
    public void reservationUpadete(ReservationUpdateReqDto reservationUpdateReqDto, CustomUserDetails customUserDetails) throws Exception{

        //수정될 예약 조회
        ReservationDto reservationDto = reservationMapper.selectReservationByReservationId(reservationUpdateReqDto.getReservationId());
        if(reservationDto == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_RESERVATION.getCode(), CommonErrorCode.NOT_FOUND_RESERVATION.getMessage()); //수업 정보를 찾을 수 없습니다.
        }

        //본인 수업만 조회 가능
        if(!reservationDto.getTrainerId().equals(customUserDetails.getTrainerId())){
            throw new CommonException(CommonErrorCode.NOT_MATCH_TRAINER.getCode(), CommonErrorCode.NOT_MATCH_TRAINER.getMessage()); //트레이너 정보가 일치하지 않습니다.
        }

        //현재보다 시간이 지난 예약은 수정 불가
        String reservationStartDateTime = reservationDto.getReservationEndDate() + reservationDto.getReservationStartTime();
        String nowDateTime = Util.getFormattedToday("yyyyMMddHHmm");
        if(Long.parseLong(reservationStartDateTime) <= Long.parseLong(nowDateTime)){
            throw new CommonException(CommonErrorCode.NOT_RESERVATION_UPDATE_FOR_TIME.getCode(), CommonErrorCode.NOT_RESERVATION_UPDATE_FOR_TIME.getMessage()); //이미 지난 수업은 수정이 불가합니다.
        }

        //날짜 체크
        Util.ticketStartEndDateChk(reservationUpdateReqDto.getReservationStartDate(), reservationUpdateReqDto.getReservationEndDate(), true);

        //시간 체크
        Util.startEndTimeChk(reservationUpdateReqDto.getReservationStartTime(), reservationUpdateReqDto.getReservationEndTime(), true, "RESERVATION");

        //예약 상태 체크(서명 및 노쇼된 수업은 수정 불가)
        if(!ReservationStatus.WAITING.toString().equals(reservationDto.getReservationStatus())){
            throw new CommonException(CommonErrorCode.ALREADY_SIGN.getCode(), CommonErrorCode.ALREADY_SIGN.getMessage()); //이미 처리된 예약입니다.
        }

        //예약 변경
        reservationDto.setReservationStartDate(reservationUpdateReqDto.getReservationStartDate());
        reservationDto.setReservationEndDate(reservationUpdateReqDto.getReservationEndDate());
        reservationDto.setReservationStartTime(reservationUpdateReqDto.getReservationStartTime());
        reservationDto.setReservationEndTime(reservationUpdateReqDto.getReservationEndTime());
        reservationDto.setReservationColor(reservationUpdateReqDto.getReservationColor());
        reservationDto.setReservationPush(reservationUpdateReqDto.getReservationPush());
        reservationDto.setReservationMemo(reservationUpdateReqDto.getReservationMemo());
        int updateReservationCnt = reservationMapper.updateReservation(reservationDto);

        if(updateReservationCnt < 1){
            throw new CommonException(CommonErrorCode.NOT_RESERVATION_UPDATE.getCode(), CommonErrorCode.NOT_RESERVATION_UPDATE.getMessage()); //예약 변경에 실패했습니다.
        }
    }

    //수업 삭제
    public void reservationDelete(ReservationDeleteReqDto reservationDeleteReqDto, CustomUserDetails customUserDetails) throws Exception{

        //수정될 예약 조회
        ReservationDto reservationDto = reservationMapper.selectReservationByReservationId(reservationDeleteReqDto.getReservationId());
        if(reservationDto == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_RESERVATION.getCode(), CommonErrorCode.NOT_FOUND_RESERVATION.getMessage()); //수업 정보를 찾을 수 없습니다.
        }

        //본인 수업만 조회 가능
        if(!reservationDto.getTrainerId().equals(customUserDetails.getTrainerId())){
            throw new CommonException(CommonErrorCode.NOT_MATCH_TRAINER.getCode(), CommonErrorCode.NOT_MATCH_TRAINER.getMessage()); //트레이너 정보가 일치하지 않습니다.
        }

        //예약 상태 체크(서명 및 노쇼된 수업은 삭제 불가)
        if(!ReservationStatus.WAITING.toString().equals(reservationDto.getReservationStatus())){
            throw new CommonException(CommonErrorCode.ALREADY_SIGN.getCode(), CommonErrorCode.ALREADY_SIGN.getMessage()); //이미 처리된 예약입니다.
        }

        //예약 삭제
        reservationDto.setReservationDeleteYn("Y");
        int updateReservationCnt = reservationMapper.updateReservation(reservationDto);

        if(updateReservationCnt < 1){
            throw new CommonException(CommonErrorCode.NOT_RESERVATION_UPDATE.getCode(), CommonErrorCode.NOT_RESERVATION_UPDATE.getMessage()); //예약 변경에 실패했습니다.
        }
    }
}
