package kr.co.fittnerserver.repository;


import kr.co.fittnerserver.entity.DropTrainer;
import org.springframework.data.repository.CrudRepository;

public interface DropTrainerRepository extends CrudRepository<DropTrainer, String> {

    boolean existsByAccessToken(String accessToken);
}
