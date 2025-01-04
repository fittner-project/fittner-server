package kr.co.fittnerserver.repository.common;

import kr.co.fittnerserver.entity.user.CenterJoin;
import kr.co.fittnerserver.entity.user.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CenterJoinRepository extends JpaRepository<CenterJoin,String> {
    List<CenterJoin> findAllByCenterJoinApprovalYnAndTrainer(String approvalYn, Trainer trainer);
}
