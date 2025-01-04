package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.user.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<Ticket, String> {
}
