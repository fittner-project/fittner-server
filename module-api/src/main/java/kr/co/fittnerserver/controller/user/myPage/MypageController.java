package kr.co.fittnerserver.controller.user.myPage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.myPage.response.SalesInfoResDto;
import kr.co.fittnerserver.dto.user.myPage.response.SalesResDto;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.service.user.mypage.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "myPage", description = "myPage내 기능들입니다.")
public class MypageController {

    final MyPageService myPageService;

    @Operation(summary = "수익관리 요약 조회 API", description = "수익관리 요약 API 입니다.")
    @GetMapping("/myPage/sales-info")
    public ResponseEntity<ApiResponseMessage<SalesInfoResDto>> getSalesInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.build(myPageService.getSalesInfo(customUserDetails));
    }

    @Operation(summary = "수익관리 조회 API", description = "수익관리 조회 API 입니다.")
    @GetMapping("/myPage/sales/{reservationStartMonth}")
    public ResponseEntity<ApiResponseMessage<SalesResDto>> getSales(@Parameter(name = "reservationStartMonth", description = "예약시작월", example = "202501")
                                                                    @PathVariable(name = "reservationStartMonth") String reservationStartMonth,
                                                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.build(myPageService.getSales(reservationStartMonth,customUserDetails));
    }
}
