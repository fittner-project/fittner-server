package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.user.Member;
import kr.co.fittnerserver.entity.user.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
    Optional<Ticket> findByMember(Member member);
}
