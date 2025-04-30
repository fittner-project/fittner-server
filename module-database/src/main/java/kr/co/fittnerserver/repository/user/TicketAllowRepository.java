package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.admin.TicketAllow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketAllowRepository extends JpaRepository<TicketAllow, String> {
}
