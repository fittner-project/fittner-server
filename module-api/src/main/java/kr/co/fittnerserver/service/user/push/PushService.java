package kr.co.fittnerserver.service.user.push;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.push.request.PushReadReqDto;
import kr.co.fittnerserver.dto.user.push.response.PushChkResDto;
import kr.co.fittnerserver.dto.user.push.response.PushResDto;
import kr.co.fittnerserver.mapper.user.push.PushMapper;
import kr.co.fittnerserver.results.FittnerPageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PushService {

    final PushMapper pushMapper;

    public PushChkResDto pushChk(String centerId, CustomUserDetails customUserDetails){
        return pushMapper.selectPushForNew(centerId, customUserDetails.getTrainerId());
    }

    public List<PushResDto> getPushs(String centerId, CustomUserDetails customUserDetails){
        return pushMapper.selectPushByTrainerIdNew(customUserDetails.getTrainerId(),centerId);
    }

    public void pushRead(PushReadReqDto pushReadReqDto, CustomUserDetails customUserDetails) throws Exception{
        pushMapper.updatePushByPushId(pushReadReqDto.getPushId(), customUserDetails.getTrainerId());
    }
}
