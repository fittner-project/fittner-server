package kr.co.fittnerserver.mapper.user.push;

import kr.co.fittnerserver.dto.user.push.response.PushChkResDto;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface PushMapper {

    PushChkResDto pushChk(String trainerId);

}
