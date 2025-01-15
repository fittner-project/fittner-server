package kr.co.fittnerserver.mapper.common;

import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.common.CommonCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
    String selectUUID();
    String reservationId();
    CommonCode selectCommonCodeByGrpCommonCodeAndCommonCode(String grpCommonCode, String commonCode);
    Center selectCenterByCenterId(String centerId);
}
