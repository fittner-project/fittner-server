package kr.co.fittnerserver.repository;


import kr.co.fittnerserver.entity.BlackListToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BlackListTokenRepository extends CrudRepository<BlackListToken, String> {
    boolean existsByAccessToken(String accessToken);

    Optional<BlackListToken> findByTrainerId();
}
