package kr.co.fittnerserver.controller.user.sign;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.service.user.sign.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user/sign")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "서명", description = "서명 및 노쇼 처리입니다.")
public class SignController {

    private final SignService signService;

    @Operation(summary = "예약 리스트 조회 API", description = "예약 리스트 조회 API 입니다.")
    @GetMapping("/reservations")
    public ResponseEntity<?> getReservations(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.build(signService.getReservations(customUserDetails));
    }
}
