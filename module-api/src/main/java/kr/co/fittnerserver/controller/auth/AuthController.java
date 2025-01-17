package kr.co.fittnerserver.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.user.TesttDto;
import kr.co.fittnerserver.dto.user.user.request.AccessTokenReqDto;
import kr.co.fittnerserver.dto.user.user.request.AppleInfoReqDto;
import kr.co.fittnerserver.dto.user.user.request.AppleRedirectReqDto;
import kr.co.fittnerserver.dto.user.user.request.LoginRequestDto;
import kr.co.fittnerserver.dto.user.user.response.AppleInfoResDto;
import kr.co.fittnerserver.dto.user.user.response.TokenResDto;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.service.auth.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "권한", description = "권한 처리입니다.")
public class AuthController {

    private final LoginService loginService;

    @PostMapping(value = "/apple-redirect-url")
    public void appleRedirectUrl(@ModelAttribute AppleRedirectReqDto appleRedirectReqDto) {
        log.info("appleRedirectUrl : {}", appleRedirectReqDto);
            //return loginService.test(appleInfoReqDto.getCode());
        }

/*
    @Operation(summary = "애플 로그인 시 필요한 유저이메일 전달 API", description = "애플 로그인 시 필요한 유저이메일 전달 API 입니다.")
    @PostMapping("/apple-info")
    public ResponseEntity<ApiResponseMessage<AppleInfoResDto>> appleInfo(@RequestBody AppleInfoReqDto appleInfoReqDto) throws Exception {
        return FittnerResponse.build(loginService.appleInfo(appleInfoReqDto));
    }*/

        @Operation(summary = "트레이너 로그인 API", description = "트레이너 로그인 API 입니다.")
        @PostMapping("/login")
        public ResponseEntity<ApiResponseMessage<TokenResDto>> login (@RequestBody LoginRequestDto loginRequestDto) throws
        Exception {
            return FittnerResponse.build(loginService.loginProcess(loginRequestDto));
        }

        @Operation(summary = "트레이너 로그아웃 API", description = "트레이너 로그아웃 API 입니다.", security = @SecurityRequirement(name = "Authorization"))
        @PostMapping("/logout")
        public ResponseEntity<ApiResponseMessage<Object>> logout (HttpServletRequest
        request, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
            loginService.logoutProcess(request, customUserDetails);
            return FittnerResponse.ok();
        }

        @Operation(summary = "엑세스 토큰 재발급 API", description = "엑세스 토큰을 재발급 받습니다.")
        @PostMapping("/refresh-token")
        public ResponseEntity<ApiResponseMessage<TokenResDto>> makeAccessToken (@RequestBody AccessTokenReqDto
        accessTokenReqDto) throws Exception {
            return FittnerResponse.build(loginService.makeToken(accessTokenReqDto));
        }


        @Operation(hidden = true)
        @GetMapping("/list-test")
        public ResponseEntity<?> listTest () {
            return FittnerResponse.build(loginService.listTest());
        }
        @Operation(hidden = true)
        @GetMapping("/page-test")
        public ResponseEntity<?> pageTest (@ModelAttribute FittnerPageable pageable){
            return FittnerResponse.buildPage(loginService.pageTest(pageable.getPageable()), pageable);
        }
        @Operation(hidden = true)
        @GetMapping("/mybatis-test")
        public ResponseEntity<?> mybatisTest () {
            return FittnerResponse.build(loginService.mybatisTest());
        }

    }
