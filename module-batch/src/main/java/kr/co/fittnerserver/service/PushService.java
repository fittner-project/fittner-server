package kr.co.fittnerserver.service;

import kr.co.fittnerserver.entity.user.Reservation;
import kr.co.fittnerserver.entity.user.enums.ReservationPush;
import kr.co.fittnerserver.entity.user.enums.ReservationStatus;
import kr.co.fittnerserver.repository.user.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushService {

    private final ReservationRepository reservationRepository;


    public void sendPreReserveNoti() {
        LocalDateTime now = LocalDateTime.now();

        // DB에서 아직 알림 안 보낸 예약 조회
        List<Reservation> reservations = reservationRepository.findAllByReservationPushAndReservationStatus(ReservationPush.before_5m, ReservationStatus.WAITING);
        log.info("5분전에 보낼 대상 갯수 :{}", reservations.size());

        for (Reservation reservation : reservations) {
            // 예약 시작 시간 LocalDateTime 변환
            LocalDateTime reservationStart = LocalDateTime.parse(
                    reservation.getReservationStartDate() + reservation.getReservationStartTime(),
                    DateTimeFormatter.ofPattern("yyyyMMddHHmm")
            );

            // 예약 시작 시간 기준 5분 전 알림 시각
            LocalDateTime notifyTime = reservationStart.minusMinutes(5);
            log.info("notifyTime :{}", notifyTime);

            // 지금이 알림 시각 범위 안이면 (1분 단위)
            if (!now.isBefore(notifyTime) && now.isBefore(notifyTime.plusMinutes(1))) {
                log.info("TEST :{}", reservation.getReservationId());
            }
                /*// 푸시 발송
                pushService.sendPreNotification(reservation);

                // 알림 발송 처리
                reservation.setPreNotificationSent("Y");
                reservationRepository.save(reservation);*/
            }
        }
    }

