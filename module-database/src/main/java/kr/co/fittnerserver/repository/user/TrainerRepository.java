package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.entity.user.enums.TrainerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer,String> {
    Optional<Trainer> findByTrainerEmail(String trainerEmail);
    boolean existsByTrainerEmail(String encrypt);

    List<Trainer> findAllByTrainerStatus(TrainerStatus trainerStatus);
}
