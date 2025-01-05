package kr.co.fittnerserver.mapper.user;

import kr.co.fittnerserver.dto.user.user.TestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrainerMapper {
    List<TestDto> selectTrainerInfo();
}
