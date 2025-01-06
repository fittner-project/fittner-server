package kr.co.fittnerserver.controller.user.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.common.response.HardUpdateResDto;
import kr.co.fittnerserver.dto.user.common.response.StatusChkResDto;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.service.user.common.UserCommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user/common")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "공통", description = "공통")
public class UserCommonController {

    private final UserCommonService userCommonService;

    @Operation(summary = "앱 버전 확인 조회 API", description = "앱 버전 확인 API 입니다.")
    @GetMapping("/app/version-chk")
    public ResponseEntity<ApiResponseMessage<HardUpdateResDto>> hardUpdate(@Parameter(description = "앱os종류 (AOS | IOS)", example = "AOS")
                                                                           @RequestParam(value = "appOsType") String appOsType,
                                                                           @Parameter(description = "앱버전", example = "1.0.1")
                                                                           @RequestParam(value = "appVersion") String appVersion) throws Exception {
        return FittnerResponse.build(userCommonService.hardUpdate(appOsType, appVersion));
    }

    @Operation(summary = "트레이너 상태 체크 API", description = "트레이너 상태 체크 API 입니다.</br>" +
                                                             "(trainerStatus 응답 필드가 'STOP'일때만 잠금팝업)")
    @GetMapping("/status-chk")
    public ResponseEntity<ApiResponseMessage<StatusChkResDto>> statusChk(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.build(userCommonService.statusChk(customUserDetails));
    }
}