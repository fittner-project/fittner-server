package kr.co.fittnerserver.mapper.user.user;

import kr.co.fittnerserver.domain.user.TrainerDto;
import kr.co.fittnerserver.dto.user.user.TestDto;
import kr.co.fittnerserver.dto.user.user.response.MemberDetailResDto;
import kr.co.fittnerserver.dto.user.user.response.TermsResDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<TestDto> selectTrainerInfo();

    TrainerDto selectTrainerByTrainerId(String trainerId);

    List<TermsResDto> selectTermsForJoin();

    List<MemberDetailResDto> selectMemberTicketDetailInfo(String memberId);
}
