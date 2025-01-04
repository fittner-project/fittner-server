package kr.co.fittnerserver.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.service.user.UserCommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "공통", description = "공통")
public class UserCommonController {

    final UserCommonService userCommonService;

    @Operation(summary = "앱 버전 확인 조회 API", description = "앱 버전 확인 API 입니다.")
    @GetMapping("/app/version-chk")
    public ResponseEntity<?> hardUpdate(@RequestParam(value = "appOsType") String appOsType,
                                        @RequestParam(value = "appVersion") String appVersion) throws Exception {
        return FittnerResponse.build(userCommonService.hardUpdate(appOsType,appVersion));
    }
}
