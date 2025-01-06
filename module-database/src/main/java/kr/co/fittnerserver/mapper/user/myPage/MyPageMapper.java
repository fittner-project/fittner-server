package kr.co.fittnerserver.mapper.user.myPage;

import kr.co.fittnerserver.dto.user.myPage.response.SalesInfoResDto;
import kr.co.fittnerserver.dto.user.myPage.response.SalesResDto;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MyPageMapper {

    SalesInfoResDto getSalesInfo(String trainerId, String reservationStartMonth);

    SalesResDto getSales(String trainerId, String reservationStartMonth, int currentPageNo);

}
