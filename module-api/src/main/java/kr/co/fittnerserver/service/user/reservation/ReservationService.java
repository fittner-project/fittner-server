package kr.co.fittnerserver.service.user.reservation;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationReqDto;
import kr.co.fittnerserver.dto.user.reservation.response.ReservationColorResDto;
import kr.co.fittnerserver.entity.user.Member;
import kr.co.fittnerserver.entity.user.Reservation;
import kr.co.fittnerserver.entity.user.Ticket;
import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.entity.user.enums.ReservationColor;
import kr.co.fittnerserver.repository.common.CenterRepository;
import kr.co.fittnerserver.repository.user.MemberRepository;
import kr.co.fittnerserver.repository.user.ReservationRepository;
import kr.co.fittnerserver.repository.user.TicketRepository;
import kr.co.fittnerserver.repository.user.TrainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final TicketRepository ticketRepository;
    private final TrainerRepository trainerRepository;

    public ReservationColorResDto getColors() {
        return new ReservationColorResDto(ReservationColor.getColorCodes());
    }

    @Transactional
    public void reservation(ReservationReqDto reservationReqDto, CustomUserDetails customUserDetails) {
        Member member = memberRepository.findById(reservationReqDto.getMemberId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_MEMBER.getCode(), CommonErrorCode.NOT_FOUND_MEMBER.getMessage()));

        Ticket ticket = ticketRepository.findByMember(member)
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TICKET.getCode(), CommonErrorCode.NOT_FOUND_TICKET.getMessage()));

        Trainer trainer = trainerRepository.findById(customUserDetails.getTrainerId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()));

        reservationRepository.save(new Reservation(reservationReqDto,member,ticket,trainer));
    }
}
