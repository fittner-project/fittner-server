package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.entity.user.TrainerRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRefreshTokenRepository extends JpaRepository<TrainerRefreshToken,String> {
    Optional<TrainerRefreshToken> findByTrainer(Trainer trainer);
}
