package kr.co.fittnerserver.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.JoinReqDto;
import kr.co.fittnerserver.dto.user.MemberListResDto;
import kr.co.fittnerserver.dto.user.MemberRegisterReqDto;
import kr.co.fittnerserver.dto.user.UserCenterListResDto;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.results.PageResponseDto;
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
        return FittnerResponse.ok();
    }

    @Operation(summary = "트레이너가 지정한 센터 목록 조회 API", description = "트레이너가 지정한 센터 목록 조회 API 입니다.")
    @GetMapping("/centers")
    public ResponseEntity<ApiResponseMessage<List<UserCenterListResDto>>> centerList(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.build(userService.getCenterList(customUserDetails));
    }

    @Operation(summary = "트레이너가 회원을 등록하는 API", description = "트레이너가 회원을 등록하는 API 입니다.")
    @PostMapping("/register")
    public ResponseEntity<ApiResponseMessage<Object>> register(@RequestBody MemberRegisterReqDto memberRegisterReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        userService.registerUser(memberRegisterReqDto,customUserDetails);
        return FittnerResponse.ok();
    }

    @Operation(summary = "회원 목록 조회 API", description = "회원 목록 조회 API 입니다.")
    @GetMapping("/members")
    public ResponseEntity<ApiResponseMessage<PageResponseDto<MemberListResDto>>> members(@ModelAttribute FittnerPageable pageable, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildPage(userService.getMembers(customUserDetails,pageable.getPageable()),pageable);
    }
}
