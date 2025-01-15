package kr.co.fittnerserver.mapper.user.myPage;

import kr.co.fittnerserver.dto.user.myPage.response.NoticeResDto;
import kr.co.fittnerserver.dto.user.myPage.response.SalesDetailResDto;
import kr.co.fittnerserver.dto.user.myPage.response.SalesInfoResDto;
import kr.co.fittnerserver.dto.user.myPage.response.SalesResDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MyPageMapper {

    SalesInfoResDto getSalesInfo(String trainerId, String reservationStartMonth);

    List<SalesResDto> getSales(String trainerId, String reservationStartMonth, int currentPageNo);

    List<SalesDetailResDto> getSalesDetail(String trainerId, String ticketId, String reservationStartMonth, int currentPageNo);

    List<NoticeResDto> selectNoticeByCenterIdAndTrainerId(int currentPageNo, String centerId, String trainerId);

    int selectNoticeReadCountByNoticeIdAndTrainerId(String noticeId, String trainerId);

    void insertNoticeRead(String noticeId, String trainerId, String centerId);

    int selectNoticeCountByNoticeId(String noticeId);
}
