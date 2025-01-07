package kr.co.fittnerserver.controller.user.myPage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.myPage.response.SalesDetailResDto;
import kr.co.fittnerserver.dto.user.myPage.response.SalesInfoResDto;
import kr.co.fittnerserver.dto.user.myPage.response.SalesResDto;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.service.user.mypage.MyPageService;
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
    public ResponseEntity<ApiResponseMessage<List<SalesResDto>>> getSales(@Parameter(name = "reservationStartMonth", description = "예약시작월", example = "202501")
                                                                    @PathVariable(name = "reservationStartMonth") String reservationStartMonth,
                                                                          @ModelAttribute FittnerPageable pageable,
                                                                          @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(myPageService.getSales(reservationStartMonth,customUserDetails,pageable));
    }

    @Operation(summary = "수익관리 상세 조회 API", description = "수익관리 상세 조회 API 입니다.")
    @GetMapping("/myPage/sales/detail")
    public ResponseEntity<ApiResponseMessage<List<SalesDetailResDto>>> getSalesDetail(@Parameter(name = "reservationStartMonth", description = "예약시작월", example = "202501")
                                                                                      @RequestParam(value = "reservationStartMonth") String reservationStartMonth,
                                                                                      @Parameter(description = "티켓ID", example = "dfb99734-ccd9-11ef-b7c9-0242ac190002")
                                                                                      @RequestParam(value = "ticketId") String ticketId,
                                                                                      @ModelAttribute FittnerPageable pageable,
                                                                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(myPageService.getSalesDetail(reservationStartMonth,ticketId,customUserDetails,pageable));
    }
}