package kr.co.fittnerserver.mapper.user.myPage;

import kr.co.fittnerserver.dto.user.myPage.response.SalesInfoResDto;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MyPageMapper {

    SalesInfoResDto getSalesInfo(String trainerId, String reservationStartDate);

}
