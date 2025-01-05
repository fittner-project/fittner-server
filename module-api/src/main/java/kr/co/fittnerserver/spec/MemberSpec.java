package kr.co.fittnerserver.spec;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import kr.co.fittnerserver.dto.user.MemberListSearchDto;
import kr.co.fittnerserver.entity.user.Member;
import kr.co.fittnerserver.entity.user.Trainer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class MemberSpec {


    public static Specification<Member> searchMember(MemberListSearchDto requestDto, Trainer trainer) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(requestDto.getMemberName())) {
                predicates.add(cb.like(root.get("memberName"), "%" + requestDto.getMemberName() + "%"));
            }
            if (StringUtils.hasText(requestDto.getMemberNo())) {
                predicates.add(cb.like(root.get("memberPhoneEnd"), "%" + requestDto.getMemberNo()));
            }
            //default
            predicates.add(cb.equal(root.get("trainer"), trainer));
            predicates.add(cb.equal(root.get("memberDeleteYn"), "N"));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
