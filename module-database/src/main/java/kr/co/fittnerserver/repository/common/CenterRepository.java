package kr.co.fittnerserver.repository.common;

import kr.co.fittnerserver.entity.admin.Center;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CenterRepository extends JpaRepository<Center, String> {
    List<Center> findAllByCenterDeleteYn(String deleteYn);
}
