package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.user.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer,String> {
    //배포
    Optional<Trainer> findByTrainerEmail(String trainerEmail);

    boolean existsByTrainerEmail(String encrypt);
}
