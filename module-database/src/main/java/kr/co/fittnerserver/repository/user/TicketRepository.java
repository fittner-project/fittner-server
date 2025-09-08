package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.user.Member;
import kr.co.fittnerserver.entity.user.Ticket;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findByMember(Member member);

    @Modifying
    @Query(value = """
        UPDATE ticket 
        SET ticket_code = 'AFTER'
        WHERE STR_TO_DATE(:today, '%Y%m%d') NOT BETWEEN 
              STR_TO_DATE(ticket_start_date, '%Y%m%d') 
              AND STR_TO_DATE(ticket_end_date, '%Y%m%d')
          AND ticket_code <> 'AFTER'
        """, nativeQuery = true)
    int updateExpiredTickets(@Param("today") String today);
}
