package kr.co.fittnerserver.mapper.common;

import kr.co.fittnerserver.domain.user.ApiLogDto;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.common.CommonCode;
import kr.co.fittnerserver.entity.user.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonMapper {
    String selectUUID();
    String reservationId();
    CommonCode selectCommonCodeByGrpCommonCodeAndCommonCode(String grpCommonCode, String commonCode);
    Center selectCenterByCenterId(String centerId);
    Member selectMemberByMemberId(String memberId);
    List<Member> selectMemberByCenterIdAndTrainerId(String centerId, String trainerId);
    void insertApiLog(ApiLogDto apiLogDto);
    void updateApiLog(ApiLogDto apiLogDto);
}
