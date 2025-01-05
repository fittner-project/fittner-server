package kr.co.fittnerserver.service.user.common;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.common.response.HardUpdateResDto;
import kr.co.fittnerserver.dto.user.common.response.StatusChkResDto;
import kr.co.fittnerserver.entity.common.AppVersion;
import kr.co.fittnerserver.mapper.user.common.UserCommonMapper;
import kr.co.fittnerserver.repository.common.CenterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCommonService {

    private final UserCommonMapper userCommonMapper;
    private final CenterRepository centerRepository;

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
}
