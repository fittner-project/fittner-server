package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.user.Member;
import kr.co.fittnerserver.entity.user.Trainer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, String> {
    Page<Member> findAllByTrainer(Trainer trainer, Pageable pageable);
}
