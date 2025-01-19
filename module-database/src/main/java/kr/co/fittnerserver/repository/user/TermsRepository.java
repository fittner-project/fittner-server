package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.common.Terms;
import kr.co.fittnerserver.entity.common.enums.TermsKind;
import kr.co.fittnerserver.entity.common.enums.TermsState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TermsRepository extends JpaRepository<Terms, String> {
    Optional<Terms> findByTermsKindAndTermsState(TermsKind termsKind, TermsState termsState);
}
