package kr.co.fittnerserver.service.user.mypage;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.domain.user.TermsDto;
import kr.co.fittnerserver.domain.user.TrainerDto;
import kr.co.fittnerserver.dto.user.myPage.requset.NoticeReadReqDto;
import kr.co.fittnerserver.dto.user.myPage.requset.PushSetReqDto;
import kr.co.fittnerserver.dto.user.myPage.response.*;
import kr.co.fittnerserver.mapper.common.CommonMapper;
import kr.co.fittnerserver.mapper.user.myPage.MyPageMapper;
import kr.co.fittnerserver.mapper.user.user.UserMapper;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.util.AES256Cipher;
import kr.co.fittnerserver.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    final MyPageMapper myPageMapper;
    final UserMapper userMapper;
    final CommonMapper commonMapper;

    public SalesResDto getSales(String centerId, String reservationStartMonth, CustomUserDetails customUserDetails) throws Exception{
        //예약시작월 없을 경우 기본값
        if(StringUtils.isEmpty(reservationStartMonth)){
            reservationStartMonth = Util.getFormattedToday("yyyyMMdd");
        }

        return myPageMapper.selectReservationForSalesHeader(centerId, customUserDetails.getTrainerId(), reservationStartMonth);
    }

    public List<SalesInfoResDto> getSalesInfo(String centerId, String reservationStartMonth, CustomUserDetails customUserDetails, FittnerPageable pageable) throws Exception{
        return myPageMapper.selectReservationForSalesBody(centerId, customUserDetails.getTrainerId(), reservationStartMonth, pageable.getCurrentPageNo());
    }

    public List<SalesInfoDetailResDto> getSalesInfoDetail(String reservationMonth, String ticketId, CustomUserDetails customUserDetails, FittnerPageable pageable) throws Exception{
        List<SalesInfoDetailDto> salesInfoDetailDtoList = myPageMapper.selectReservationForSalesBodyDetail(ticketId, customUserDetails.getTrainerId(), reservationMonth, pageable.getCurrentPageNo());

        // 예약월 기준으로 그룹화
        List<SalesInfoDetailResDto> reservationList = salesInfoDetailDtoList.stream()
                .collect(Collectors.groupingBy(SalesInfoDetailDto::getReservationMonth)) // 예약월로 그룹화
                .entrySet().stream()
                .map(entry -> {
                    SalesInfoDetailResDto dto = new SalesInfoDetailResDto();
                    dto.setReservationMonth(entry.getKey());
                    dto.setReservationListList(entry.getValue());
                    return dto;
                })
                .collect(Collectors.toList());

        return reservationList;
    }

    public List<NoticeResDto> getNotices(String centerId, CustomUserDetails customUserDetails, FittnerPageable pageable) throws Exception {
        return myPageMapper.selectNoticeByCenterIdAndTrainerId(pageable.getCurrentPageNo(), centerId, customUserDetails.getTrainerId());
    }

    public void noticeRead(NoticeReadReqDto noticeReadReqDto, CustomUserDetails customUserDetails) throws Exception{
        //공지사항ID 체크
        if(myPageMapper.selectNoticeCountByNoticeId(noticeReadReqDto.getNoticeId()) > 0){

            //공지사항 읽음 테이블 있는지 체크
            int noticeReadChk = myPageMapper.selectNoticeReadCountByNoticeIdAndTrainerId(noticeReadReqDto.getNoticeId(), customUserDetails.getTrainerId());
            if(noticeReadChk == 0){
                TrainerDto trainerDto = userMapper.selectTrainerByTrainerId(customUserDetails.getTrainerId());
                myPageMapper.insertNoticeRead(noticeReadReqDto.getNoticeId(), customUserDetails.getTrainerId(), trainerDto.getCenterId());
            }
        }
    }

    public List<TermsListResDto> getTerms(CustomUserDetails customUserDetails) throws Exception {
        List<TermsListResDto> r = new ArrayList<>();

        //적용중인 약관
        List<TermsDto> ingTerms = myPageMapper.selectTerms("ING","TOTAL");

        for(TermsDto ingTerm : ingTerms){
            //적용중 약관 정보
            TermsListResDto data = new TermsListResDto();
            data.setIngTermsTitle(ingTerm.getTermsTitle());
            data.setIntTermsStartDate(ingTerm.getTermsStartDate());

            //이전 약관 정보
            List<TermsDto> allTerms = myPageMapper.selectTerms("TOTAL",ingTerm.getTermsKind());
            List<TermsListResDto.TotalTerm> beforeTermsStartDate = new ArrayList<>();
            for(TermsDto termsDto : allTerms){
                TermsListResDto.TotalTerm terms = new TermsListResDto.TotalTerm();
                terms.setTermsStartDate(termsDto.getTermsStartDate());
                terms.setTermsUrl(termsDto.getFileUrl());
                beforeTermsStartDate.add(terms);
                data.setTotalTermList(beforeTermsStartDate);
            }
            r.add(data);
        }

        return r;
    }

    public void setPush(PushSetReqDto pushSetReqDto, CustomUserDetails customUserDetails) throws Exception{
        myPageMapper.updatePushSet(pushSetReqDto.getPushKind(), pushSetReqDto.getPushSetYn(), customUserDetails.getTrainerId());

        //마케팅 알림일 경우 약관 동의 업데이트
        if("ADVERTISE".equals(pushSetReqDto.getPushKind())){
            //현재 적용중인 마케팅 약관ID
            List<TermsDto> termsDto = myPageMapper.selectTerms("ING", "ADVERTISE");
            myPageMapper.updateTermsAgree(customUserDetails.getTrainerId(), termsDto.get(0).getTermsId());
        }
    }

    public List<PushSetResDto> getPush(CustomUserDetails customUserDetails) throws Exception{
        return myPageMapper.selectPushSetByTrainerId(customUserDetails.getTrainerId());
    }

    public MyPageInfoResDto myPageInfo(CustomUserDetails customUserDetails) throws Exception{
        MyPageInfoResDto r = new MyPageInfoResDto();

        TrainerDto trainerDto = userMapper.selectTrainerByTrainerId(customUserDetails.getTrainerId());

        r.setTrainerEmail(AES256Cipher.decrypt(trainerDto.getTrainerEmail()));
        r.setTrainerName(trainerDto.getTrainerName());
        r.setTrainerSnsKind(trainerDto.getTrainerSnsKind());

        return r;
    }


}
