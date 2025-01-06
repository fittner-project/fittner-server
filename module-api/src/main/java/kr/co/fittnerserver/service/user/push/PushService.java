package kr.co.fittnerserver.service.user.push;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.push.response.PushChkResDto;
import kr.co.fittnerserver.mapper.user.push.PushMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PushService {

    final PushMapper pushMapper;

    public PushChkResDto pushChk(CustomUserDetails customUserDetails){
        return pushMapper.pushChk(customUserDetails.getTrainerId());
    }
}
