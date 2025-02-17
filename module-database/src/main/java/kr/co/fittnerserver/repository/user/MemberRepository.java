package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.entity.user.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, String> , JpaSpecificationExecutor<Member> {

    @Query("""
    SELECT m from Member m
    WHERE m.trainer.trainerId = :trainerId
    order by m.createdDate desc
""")
    List<Member> findAllByTrainer(@Param(value = "trainerId") String trainerId);
}
