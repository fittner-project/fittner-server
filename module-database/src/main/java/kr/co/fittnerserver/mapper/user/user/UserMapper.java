package kr.co.fittnerserver.mapper.user.user;

import kr.co.fittnerserver.dto.user.user.TestDto;
import kr.co.fittnerserver.entity.user.Trainer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<TestDto> selectTrainerInfo();

    Trainer selectTrainerByTrainerId(String trainerId);
}