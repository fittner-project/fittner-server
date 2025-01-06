package kr.co.fittnerserver.service.user.mypage;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.myPage.response.SalesInfoResDto;
import kr.co.fittnerserver.mapper.user.myPage.MyPageMapper;
import kr.co.fittnerserver.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    final MyPageMapper myPageMapper;

    public SalesInfoResDto getSalesInfo(CustomUserDetails customUserDetails){
        return myPageMapper.getSalesInfo(customUserDetails.getTrainerId(), Util.getFormattedToday("yyyyMmdd"));
    }
}
