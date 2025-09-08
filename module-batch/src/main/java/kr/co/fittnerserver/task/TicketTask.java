package kr.co.fittnerserver.task;


import kr.co.fittnerserver.entity.user.Ticket;
import kr.co.fittnerserver.entity.user.enums.TicketCode;
import kr.co.fittnerserver.repository.user.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketTask {

    private final TicketRepository ticketRepository;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Scheduled(cron = "0 0/1 * * * ?", zone = "Asia/Seoul") // 1분마다 실행
    @Transactional
    public void updateTicketStatusJob() {
        String today = LocalDate.now().format(FORMATTER);
        int updated = ticketRepository.updateExpiredTickets(today);
        log.info("만료 범위 밖 티켓 STOP 처리: {}건", updated);
    }
}
