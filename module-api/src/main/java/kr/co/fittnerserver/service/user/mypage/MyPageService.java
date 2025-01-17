package kr.co.fittnerserver.service.user.mypage;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.domain.user.TermsDto;
import kr.co.fittnerserver.domain.user.TrainerDto;
import kr.co.fittnerserver.dto.user.myPage.requset.NoticeReadReqDto;
import kr.co.fittnerserver.dto.user.myPage.response.*;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.common.Terms;
import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.mapper.common.CommonMapper;
import kr.co.fittnerserver.mapper.user.myPage.MyPageMapper;
import kr.co.fittnerserver.mapper.user.user.UserMapper;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    final MyPageMapper myPageMapper;
    final UserMapper userMapper;
    final CommonMapper commonMapper;

    public SalesInfoResDto getSalesInfo(CustomUserDetails customUserDetails) throws Exception{
        return myPageMapper.getSalesInfo(customUserDetails.getTrainerId(), Util.getFormattedToday("yyyyMmdd"));
    }

    public List<SalesResDto> getSales(String reservationStartMonth, CustomUserDetails customUserDetails, FittnerPageable pageable) throws Exception{
        return myPageMapper.getSales(reservationStartMonth, customUserDetails.getTrainerId(), pageable.getCurrentPageNo());
    }

    public List<SalesDetailResDto> getSalesDetail(String reservationStartMonth, String ticketId, CustomUserDetails customUserDetails, FittnerPageable pageable) throws Exception{
        return myPageMapper.getSalesDetail(reservationStartMonth, ticketId, customUserDetails.getTrainerId(), pageable.getCurrentPageNo());
    }

    public List<NoticeResDto> getNotices(CustomUserDetails customUserDetails, FittnerPageable pageable) throws Exception {
        TrainerDto trainer = userMapper.selectTrainerByTrainerId(customUserDetails.getTrainerId());
        return myPageMapper.selectNoticeByCenterIdAndTrainerId(pageable.getCurrentPageNo(), trainer.getCenterId(), customUserDetails.getTrainerId());
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


}
