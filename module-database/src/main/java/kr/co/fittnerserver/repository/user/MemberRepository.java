package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member,String> {
}
