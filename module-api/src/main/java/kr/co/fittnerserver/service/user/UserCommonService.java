package kr.co.fittnerserver.service.user;

import kr.co.fittnerserver.dto.user.response.HardUpdateResDto;
import kr.co.fittnerserver.entity.common.AppVersion;
import kr.co.fittnerserver.mapper.user.UserCommonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCommonService {

    final UserCommonMapper userCommonMapper;

    public HardUpdateResDto hardUpdate(String appOsType, String appVersion) throws Exception {
        HardUpdateResDto r = new HardUpdateResDto();

        AppVersion appVersionChk = userCommonMapper.selectAppVersionByAppOs(appOsType,appVersion);

        if(appVersionChk != null){
            r.setHardUpdateYn(appVersionChk.getHardUpdateYn());
            r.setAppUpadateUrl(appVersionChk.getUpdateUrl());
        }else{
            r.setHardUpdateYn("N");
        }

        return r;
    }
}
