package kr.co.fittnerserver.repository.common;

import kr.co.fittnerserver.entity.common.RefreshToken;
import kr.co.fittnerserver.entity.user.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {
    Optional<RefreshToken> findByTrainer(Trainer trainer);
}
