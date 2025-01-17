package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.user.Member;
import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.entity.user.TrainerProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TrainerProductRepository extends JpaRepository<TrainerProduct,String> {
    List<TrainerProduct> findAllByTrainerAndMember(Trainer trainer, Member member);


    /*@Query(value = """
    SELECT
""")
    void getMemberDetailInfo(String memberId);*/
}
