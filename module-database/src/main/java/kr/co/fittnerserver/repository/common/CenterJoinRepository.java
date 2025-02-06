package kr.co.fittnerserver.repository.common;

import kr.co.fittnerserver.entity.user.CenterJoin;
import kr.co.fittnerserver.entity.user.Trainer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CenterJoinRepository extends JpaRepository<CenterJoin, String> {
    Page<CenterJoin> findAllByCenterJoinApprovalYnAndTrainer(String approvalYn, Trainer trainer, Pageable pageable);

    List<CenterJoin> findAllByTrainer(Trainer trainer);

    void deleteById(String centerJoinId);

}
