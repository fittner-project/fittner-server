package kr.co.fittnerserver.service.user.main;


import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationSearchDto;
import kr.co.fittnerserver.dto.user.reservation.response.GroupedReservationDto;
import kr.co.fittnerserver.dto.user.reservation.response.MainShortsReservationResDto;
import kr.co.fittnerserver.dto.user.user.response.MainUserCenterListResDto;
import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.repository.common.CenterJoinRepository;
import kr.co.fittnerserver.repository.user.ReservationRepository;
import kr.co.fittnerserver.repository.user.TrainerRepository;
import kr.co.fittnerserver.results.CacheablePage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {

    private final TrainerRepository trainerRepository;
    private final CenterJoinRepository centerJoinRepository;
    private final ReservationRepository reservationRepository;


    @Transactional(readOnly = true)
    public Page<MainUserCenterListResDto> getMainCenterListByTrainer(CustomUserDetails customUserDetails, Pageable pageable) {
        Trainer trainer = trainerRepository.findById(customUserDetails.getTrainerId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()));

        return new CacheablePage<>(
                centerJoinRepository.findAllByCenterJoinApprovalYnAndTrainer("N", trainer, pageable)
                        .map(userCenter -> MainUserCenterListResDto.builder()
                                .centerJoinId(userCenter.getCenterJoinId())
                                .centerName(userCenter.getCenter().getCenterName())
                                .build()));
    }

    @Transactional(readOnly = true)
    public List<GroupedReservationDto> getMainShortSchedules(ReservationSearchDto reservationSearchDto, CustomUserDetails customUserDetails) {
        return getGroupedSchedules(reservationSearchDto.getReservationStartDate(), reservationSearchDto.getReservationEndDate(), customUserDetails.getTrainerId());
    }


    public List<GroupedReservationDto> getGroupedSchedules(String startDate, String endDate, String trainerId) {
        // 데이터 조회
        List<MainShortsReservationResDto> reservations = reservationRepository.getMainShortSchedules(startDate, endDate, trainerId);

        // lastTwoDigits 기준으로 그룹화하여 새로운 구조로 변환
        return reservations.stream()
                .collect(Collectors.groupingBy(MainShortsReservationResDto::getLastTwoDigits))
                .entrySet()
                .stream()
                .map(entry ->
                        new GroupedReservationDto(
                                entry.getKey(),
                                entry.getValue().stream()
                                        .map(MainShortsReservationResDto::getReservationColor)
                                        .collect(Collectors.toList())
                        )
                )
                .collect(Collectors.toList());
    }
}
