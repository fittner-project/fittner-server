package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.common.Terms;
import kr.co.fittnerserver.entity.common.TermsAgree;
import kr.co.fittnerserver.entity.user.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TermsAgreeRepository extends JpaRepository<TermsAgree, String> {
    Optional<TermsAgree> findByTermsAndTrainer(Terms terms, Trainer trainer);
}
