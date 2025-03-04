package kr.co.fittnerserver.mapper.user.push;

import kr.co.fittnerserver.dto.user.push.response.PushChkResDto;
import kr.co.fittnerserver.dto.user.push.response.PushResDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface PushMapper {

    PushChkResDto selectPushForNew(String centerId, String trainerId);


    void updatePushByPushId(String pushId, String trainerId);

    List<PushResDto> selectPushByTrainerIdNew(String trainerId, String centerId);
}
