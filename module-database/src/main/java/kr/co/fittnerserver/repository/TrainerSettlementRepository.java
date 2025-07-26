package kr.co.fittnerserver.repository;

import kr.co.fittnerserver.entity.admin.TrainerSettlement;
import kr.co.fittnerserver.entity.admin.enums.TrainerSettlementCode;
import kr.co.fittnerserver.entity.user.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerSettlementRepository extends JpaRepository<TrainerSettlement,String> {

    Optional<TrainerSettlement> findByTrainerAndTrainerSettlementCode(Trainer trainer, TrainerSettlementCode trainerSettlementCode);
}
