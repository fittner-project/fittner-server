package kr.co.fittnerserver.repository.common;

import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.admin.CenterGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CenterRepository extends JpaRepository<Center, String> {
    List<Center> findAllByCenterDeleteYn(String deleteYn);

    List<Center> findAllByCenterGroupAndCenterDeleteYn(CenterGroup centerGroup, String deleteYn);
}
