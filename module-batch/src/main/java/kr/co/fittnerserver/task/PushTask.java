package kr.co.fittnerserver.task;


import kr.co.fittnerserver.repository.user.TicketRepository;
import kr.co.fittnerserver.service.PushService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class PushTask {

    private final PushService pushService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 미리 알림보내는 스케줄러 (예: 5분,10분,15분,1시간)
     */
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul") // 매 분 실행
    public void sendPreReserveNoti() {
        pushService.sendPreReserveNoti();

    }

}
