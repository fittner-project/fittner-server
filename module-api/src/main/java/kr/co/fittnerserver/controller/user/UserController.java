package kr.co.fittnerserver.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.JoinReqDto;
import kr.co.fittnerserver.dto.user.UserCenterListResDto;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.results.MtnResponse;
import kr.co.fittnerserver.service.user.UserService;
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
public class UserController {

    private final UserService userService;

    @Operation(summary = "트레이너 회원가입 API", description = "트레이너 회원가입 API 입니다.")
    @PostMapping("/join")
    public ResponseEntity<ApiResponseMessage<Object>> login(@RequestBody JoinReqDto joinReqDto) throws Exception {
        userService.joinProcess(joinReqDto);
        return MtnResponse.ok();
    }

    @Operation(summary = "트레이너가 지정한 센터 목록 조회 API", description = "트레이너가 지정한 센터 목록 조회 API 입니다.")
    @GetMapping("/centers")
    public ResponseEntity<ApiResponseMessage<List<UserCenterListResDto>>> centerList(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return MtnResponse.build(userService.getCenterList(customUserDetails));
    }

}
