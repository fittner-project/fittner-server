package kr.co.fittnerserver.repository.common;

import kr.co.fittnerserver.entity.admin.Center;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CenterRepository extends JpaRepository<Center, String> {
}
