package kr.co.fittnerserver.service.user.common;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.domain.user.TrainerDto;
import kr.co.fittnerserver.dto.user.common.response.HardUpdateResDto;
import kr.co.fittnerserver.dto.user.common.response.SplashResDto;
import kr.co.fittnerserver.dto.user.common.response.StatusChkResDto;
import kr.co.fittnerserver.entity.common.AppVersion;
import kr.co.fittnerserver.entity.common.CommonCode;
import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.mapper.common.CommonMapper;
import kr.co.fittnerserver.mapper.user.common.UserCommonMapper;
import kr.co.fittnerserver.mapper.user.user.UserMapper;
import kr.co.fittnerserver.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCommonService {

    private final UserCommonMapper userCommonMapper;
    private final UserMapper userMapper;
    private final CommonMapper commonMapper;
    private final JwtTokenUtil jwtTokenUtil;

    public HardUpdateResDto hardUpdate(String appOsType, String appVersion) throws Exception {
        HardUpdateResDto r = new HardUpdateResDto();

        AppVersion appVersionChk = userCommonMapper.selectAppVersionByAppOs(appOsType, appVersion);

        if (appVersionChk != null) {
            r.setHardUpdateYn(appVersionChk.getHardUpdateYn());
            r.setAppUpadateUrl(appVersionChk.getUpdateUrl());
        } else {
            r.setHardUpdateYn("N");
        }

        return r;
    }

    public StatusChkResDto statusChk(CustomUserDetails customUserDetails) throws Exception{
        return userCommonMapper.statusChk(customUserDetails.getTrainerId());
    }

    public SplashResDto getSplash(String accessToken){

        //응답값
        SplashResDto r = new SplashResDto();

        //기본 이미지 url
        String defaultImgUrl = "https://api.fittner.co.kr/api/v1/common/file/show/1Ec3Eb2h1B9";

        try {
            if(!StringUtils.isEmpty(accessToken)){
                TrainerDto trainer = userMapper.selectTrainerByTrainerId(jwtTokenUtil.validateTokenAndGetTrainerId(accessToken));
                if(trainer != null){
                    CommonCode commonCode = commonMapper.selectCommonCodeByGrpCommonCodeAndCommonCode("SPLASH",trainer.getCenterId());
                    r.setSplashImgUrl(commonCode.getCommonCodeMemo());
                }
            }

            if(StringUtils.isEmpty(r.getSplashImgUrl())){
                r.setSplashImgUrl(defaultImgUrl); //기본이미지
            }

        }catch (Exception e){
            r.setSplashImgUrl(defaultImgUrl); //기본이미지
        }

        return r;
    }
}
