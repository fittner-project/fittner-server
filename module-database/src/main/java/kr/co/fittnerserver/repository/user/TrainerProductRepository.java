package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.user.TrainerProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerProductRepository extends JpaRepository<TrainerProduct,String> {
}
