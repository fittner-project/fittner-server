package kr.co.fittnerserver.service.user.common;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.domain.user.TrainerDto;
import kr.co.fittnerserver.dto.user.common.response.BrandColorResDto;
import kr.co.fittnerserver.dto.user.common.response.HardUpdateResDto;
import kr.co.fittnerserver.dto.user.common.response.SplashResDto;
import kr.co.fittnerserver.dto.user.common.response.StatusChkResDto;
import kr.co.fittnerserver.entity.common.AppVersion;
import kr.co.fittnerserver.entity.common.CommonCode;
import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.mapper.common.CommonMapper;
import kr.co.fittnerserver.mapper.user.common.UserCommonMapper;
import kr.co.fittnerserver.mapper.user.user.UserMapper;
import kr.co.fittnerserver.util.AES256Cipher;
import kr.co.fittnerserver.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    public StatusChkResDto statusChk(String trainerEmail) throws Exception{
        StatusChkResDto r = new StatusChkResDto();

        r = userCommonMapper.statusChk(AES256Cipher.encrypt(trainerEmail));
        if(r == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()); //트레이너를 찾을 수 없습니다.
        }

        return r;
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
                    CommonCode commonCode = commonMapper.selectCommonCodeByGrpCommonCodeAndCommonCode("SPLASH",trainer.getCenterId()); //TODO 센터:트레이너(n:1) 이므로 고민해야함
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

    public BrandColorResDto getBrandColor(CustomUserDetails customUserDetails) throws  Exception{

        BrandColorResDto r = new BrandColorResDto();

        try {

            TrainerDto trainer = userMapper.selectTrainerByTrainerId(customUserDetails.getTrainerId());

            CommonCode commonCode = commonMapper.selectCommonCodeByGrpCommonCodeAndCommonCode("BRANDCOLOR", trainer.getCenterId()); //TODO 센터:트레이너(n:1) 이므로 고민해야함

            if (commonCode != null) {
                //format : greyTypeA=#909090&greyTypeB=#D0D0D0&greyTypeC=#92KS00
                String keyValueStr = commonCode.getCommonCodeMemo();

                Map<String, String> map = new HashMap<>();

                // 문자열을 '&'로 분리
                String[] pairs = keyValueStr.split("&");

                for (String pair : pairs) {
                    // 각 쌍을 '='로 분리
                    String[] keyValue = pair.split("=", 2); // 최대 2개로 나누기

                    if (keyValue.length == 2) {
                        String key = keyValue[0];
                        String value = keyValue[1];
                        map.put(key, value); // HashMap에 추가
                    } else if (keyValue.length == 1) {
                        // 값이 없는 키 처리 (예: "test4="의 경우)dd
                        map.put(keyValue[0], "");
                    }
                }

                r.setPrimary(map.get("primary"));
                r.setSub(map.get("sub"));
                r.setGreyTypeA(map.get("greyTypeA"));
                r.setGreyTypeB(map.get("greyTypeB"));
                r.setGreyTypeC(map.get("greyTypeC"));
                r.setGreyTypeD(map.get("greyTypeD"));
                r.setTextTypeA(map.get("textTypeA"));
                r.setTextTypeB(map.get("textTypeB"));
                r.setTextTypeB(map.get("textTypeC"));
                r.setTextTypeB(map.get("textTypeD"));
                r.setTextTypeB(map.get("textTypeE"));
                r.setTextTypeB(map.get("textTypeF"));
                r.setTextTypeB(map.get("textTypeG"));

            } else {

                //기본 컬러
                r.setPrimary("#4C6AFF");
                r.setSub("#FF8194");
                r.setGreyTypeA("#F2F4F6");
                r.setGreyTypeB("#B0B8C1");
                r.setGreyTypeC("#7F848D");
                r.setGreyTypeD("#4D5662");
                r.setTextTypeA("#191F28");
                r.setTextTypeB("#191F28");
                r.setTextTypeC("#191F28");
                r.setTextTypeD("#191F28");
                r.setTextTypeE("#191F28");
                r.setTextTypeF("#191F28");
                r.setTextTypeG("#191F28");

            }
        }catch (Exception e){
            //기본 컬러
            r.setPrimary("#4C6AFF");
            r.setSub("#FF8194");
            r.setGreyTypeA("#F2F4F6");
            r.setGreyTypeB("#B0B8C1");
            r.setGreyTypeC("#7F848D");
            r.setGreyTypeD("#4D5662");
            r.setTextTypeA("#191F28");
            r.setTextTypeB("#191F28");
            r.setTextTypeC("#191F28");
            r.setTextTypeD("#191F28");
            r.setTextTypeE("#191F28");
            r.setTextTypeF("#191F28");
            r.setTextTypeG("#191F28");
        }

        return r;
    }
}
