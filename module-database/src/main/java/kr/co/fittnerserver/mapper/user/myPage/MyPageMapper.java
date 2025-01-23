package kr.co.fittnerserver.mapper.user.myPage;

import kr.co.fittnerserver.domain.user.TermsDto;
import kr.co.fittnerserver.dto.user.myPage.response.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MyPageMapper {

    SalesResDto selectReservationForSalesHeader(String centerId, String trainerId, String reservationStartMonth);

    List<SalesInfoResDto> selectReservationForSalesBody(String centerId, String trainerId, String reservationStartMonth, int currentPageNo);

    List<SalesInfoDetailResDto> selectReservationForSalesBodyDetail(String ticketId, String trainerId, String reservationStartMonth, int currentPageNo);

    List<NoticeResDto> selectNoticeByCenterIdAndTrainerId(int currentPageNo, String centerId, String trainerId);

    int selectNoticeReadCountByNoticeIdAndTrainerId(String noticeId, String trainerId);

    void insertNoticeRead(String noticeId, String trainerId, String centerId);

    int selectNoticeCountByNoticeId(String noticeId);

    List<TermsDto> selectTerms(String termsState, String termsKind);

    void updatePushSet(String pushKind, String pushSetYn, String trainerId);

    void updateTermsAgree(String trainerId, String termsId);

    List<PushSetResDto> selectPushSetByTrainerId(String trainerId);
}
