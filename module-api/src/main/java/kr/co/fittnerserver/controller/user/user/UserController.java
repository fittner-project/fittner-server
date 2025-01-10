package kr.co.fittnerserver.controller.user.user;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.user.*;
import kr.co.fittnerserver.dto.user.user.request.CenterRegisterReqDto;
import kr.co.fittnerserver.dto.user.user.request.JoinReqDto;
import kr.co.fittnerserver.dto.user.user.request.MemberRegisterReqDto;
import kr.co.fittnerserver.dto.user.user.response.CenterListResDto;
import kr.co.fittnerserver.dto.user.user.response.MemberListResDto;
import kr.co.fittnerserver.dto.user.user.response.UserCenterListResDto;
import kr.co.fittnerserver.dto.user.user.response.UserInfoResDto;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.results.PageResponseDto;
import kr.co.fittnerserver.service.user.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "메인에서 트레이너가 지정한 센터 목록 조회 API", description = "트레이너가 지정한 센터 목록 조회 API 입니다.")
    @GetMapping("/main/centers")
    public ResponseEntity<ApiResponseMessage<PageResponseDto<UserCenterListResDto>>> centerList(@ModelAttribute FittnerPageable pageable, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildPage(userService.getCenterListByTrainer(customUserDetails, pageable.getPageable()), pageable);
    }

    @Operation(summary = "트레이너가 회원을 등록하는 API", description = "트레이너가 회원을 등록하는 API 입니다.")
    @PostMapping("/register")
    public ResponseEntity<ApiResponseMessage<Object>> register(@RequestBody MemberRegisterReqDto memberRegisterReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        userService.registerUser(memberRegisterReqDto, customUserDetails);
        return FittnerResponse.ok();
    }

    @Operation(summary = "트레이너의 회원 목록 조회 API", description = "트레이너의 회원 목록 조회 API 입니다.")
    @GetMapping("/members")
    public ResponseEntity<ApiResponseMessage<PageResponseDto<MemberListResDto>>> members(@ModelAttribute FittnerPageable pageable,
                                                                                         @ModelAttribute MemberListSearchDto memberListSearchDto,
                                                                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildPage(userService.getMembers(memberListSearchDto, customUserDetails, pageable.getPageable()), pageable);
    }


    @Operation(summary = "센터 목록 조회 API", description = "센터 목록 조회 API 입니다.")
    @GetMapping("/center/list")
    public ResponseEntity<ApiResponseMessage<PageResponseDto<CenterListResDto>>> centerList(@ModelAttribute FittnerPageable pageable) throws Exception {
        return FittnerResponse.buildPage(userService.getCenterList(pageable.getPageable()), pageable);
    }

    @Operation(summary = "트레이너 정보 조회 API", description = "트레이너 정보 조회 API 입니다.")
    @GetMapping("/info")
    public ResponseEntity<ApiResponseMessage<UserInfoResDto>> getUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.build(userService.getUserInfo(customUserDetails));
    }

    @Operation(summary = "트레이너의 센터 등록 API", description = "트레이너의 센터 등록 API 입니다.")
    @PostMapping("/center")
    public ResponseEntity<ApiResponseMessage<Object>> registerCenter(@RequestBody CenterRegisterReqDto centerRegisterReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        userService.registerCenter(centerRegisterReqDto, customUserDetails);
        return FittnerResponse.ok();
    }

    /*@Operation(summary = "트레이너가 지정한 센터 목록 조회 API", description = "트레이너가 지정한 센터 목록 조회 API 입니다.")
    @GetMapping("/centers")
    public ResponseEntity<ApiResponseMessage<PageResponseDto<UserCenterListResDto>>>*/
}
