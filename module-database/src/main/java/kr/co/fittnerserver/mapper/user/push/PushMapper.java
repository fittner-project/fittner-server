package kr.co.fittnerserver.mapper.user.push;

import kr.co.fittnerserver.dto.user.push.response.PushChkResDto;
import kr.co.fittnerserver.dto.user.push.response.PushResDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface PushMapper {

    PushChkResDto pushChk(String trainerId);

    List<PushResDto> selectPushByTrainerId(int currentPageNo, String trainerId);

    void updatePushByPushId(String pushId, String trainerId);
}
