package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.user.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,String> {
}
