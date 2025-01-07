package kr.co.fittnerserver.service.user.mypage;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.myPage.response.SalesDetailResDto;
import kr.co.fittnerserver.dto.user.myPage.response.SalesInfoResDto;
import kr.co.fittnerserver.dto.user.myPage.response.SalesResDto;
import kr.co.fittnerserver.mapper.user.myPage.MyPageMapper;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    final MyPageMapper myPageMapper;

    public SalesInfoResDto getSalesInfo(CustomUserDetails customUserDetails) throws Exception{
        return myPageMapper.getSalesInfo(customUserDetails.getTrainerId(), Util.getFormattedToday("yyyyMmdd"));
    }

    public List<SalesResDto> getSales(String reservationStartMonth, CustomUserDetails customUserDetails, FittnerPageable pageable) throws Exception{
        return myPageMapper.getSales(reservationStartMonth, customUserDetails.getTrainerId(), pageable.getCurrentPageNo());
    }

    public List<SalesDetailResDto> getSalesDetail(String reservationStartMonth, String ticketId, CustomUserDetails customUserDetails, FittnerPageable pageable) throws Exception{
        return myPageMapper.getSalesDetail(reservationStartMonth, ticketId, customUserDetails.getTrainerId(), pageable.getCurrentPageNo());
    }


}