package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.user.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer,String> {
    Optional<Trainer> findByTrainerEmail(String trainerEmail);
//배포
    boolean existsByTrainerEmail(String encrypt);
}
