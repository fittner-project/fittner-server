package kr.co.fittnerserver.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.LoginRequestDto;
import kr.co.fittnerserver.service.user.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        return ResponseEntity.ok(loginService.loginProcess(loginRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        log.info("test@@@@ : {}",customUserDetails);
        loginService.logoutProcess(request, customUserDetails);
        return ResponseEntity.ok().build();

    }


    @GetMapping("/test")
    public void get(){
        log.info("test");
    }
}
