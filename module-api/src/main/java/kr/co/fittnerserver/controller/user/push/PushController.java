package kr.co.fittnerserver.controller.user.push;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.push.request.PushReadReqDto;
import kr.co.fittnerserver.dto.user.push.response.PushChkResDto;
import kr.co.fittnerserver.dto.user.push.response.PushResDto;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.service.user.push.PushService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "푸시", description = "푸시 알람입니다.")
public class PushController {

    final PushService pushService;

    @Operation(summary = "신규 알림 여부 조회 API", description = "신규 알림이 있는지(안읽은 알림) 확인하는 API 입니다.")
    @GetMapping("/push/chk")
    public ResponseEntity<ApiResponseMessage<PushChkResDto>> pushChk(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.build(pushService.pushChk(customUserDetails));
    }

    @Operation(summary = "알림 리스트 조회 API", description = "알림 리스트 조회 API 입니다.")
    @GetMapping("/pushs")
    public ResponseEntity<ApiResponseMessage<List<PushResDto>>> getPushs(@ModelAttribute FittnerPageable pageable, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(pushService.getPushs(pageable, customUserDetails));
    }

    @Operation(summary = "알림 읽음 API", description = "알림 읽음 API 입니다.")
    @PostMapping("/push/read")
    public ResponseEntity<ApiResponseMessage<Object>> pushRead(@RequestBody PushReadReqDto pushReadReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        pushService.pushRead(pushReadReqDto, customUserDetails);
        return FittnerResponse.ok();
    }
}
