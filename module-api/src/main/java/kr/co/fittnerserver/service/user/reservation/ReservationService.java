package kr.co.fittnerserver.service.user.reservation;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.domain.user.TicketDto;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationReqDto;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationSearchDto;
import kr.co.fittnerserver.dto.user.reservation.response.GroupedReservationMemberDto;
import kr.co.fittnerserver.dto.user.reservation.response.ReservationColorResDto;
import kr.co.fittnerserver.dto.user.reservation.response.ReservationMemberResDto;
import kr.co.fittnerserver.entity.admin.TrainerSettlement;
import kr.co.fittnerserver.entity.admin.enums.TrainerSettlementCode;
import kr.co.fittnerserver.entity.user.Member;
import kr.co.fittnerserver.entity.user.Reservation;
import kr.co.fittnerserver.entity.user.Ticket;
import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.entity.user.enums.ReservationColor;
import kr.co.fittnerserver.mapper.user.ticket.TicketMapper;
import kr.co.fittnerserver.repository.TrainerSettlementRepository;
import kr.co.fittnerserver.repository.common.CenterRepository;
import kr.co.fittnerserver.repository.user.MemberRepository;
import kr.co.fittnerserver.repository.user.ReservationRepository;
import kr.co.fittnerserver.repository.user.TicketRepository;
import kr.co.fittnerserver.repository.user.TrainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (startDateTime.isAfter(endDateTime)) {
            throw new CommonException(CommonErrorCode.NOT_RESERVATION_ADD.getCode(), CommonErrorCode.NOT_RESERVATION_ADD.getMessage());
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
}
