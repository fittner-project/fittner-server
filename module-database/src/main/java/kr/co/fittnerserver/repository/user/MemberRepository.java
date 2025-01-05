package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface MemberRepository extends JpaRepository<Member, String> , JpaSpecificationExecutor<Member> {
}
