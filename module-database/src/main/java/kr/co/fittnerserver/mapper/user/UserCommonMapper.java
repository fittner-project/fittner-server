package kr.co.fittnerserver.mapper.user;

import kr.co.fittnerserver.dto.user.response.StatusChkResDto;
import kr.co.fittnerserver.entity.common.AppVersion;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserCommonMapper {
    AppVersion selectAppVersionByAppOs(String appOsType, String appVersion);

    StatusChkResDto statusChk(String trainerId);
}
