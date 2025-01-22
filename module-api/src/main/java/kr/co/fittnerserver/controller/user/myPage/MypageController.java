package kr.co.fittnerserver.controller.user.myPage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.myPage.requset.NoticeReadReqDto;
import kr.co.fittnerserver.dto.user.myPage.requset.PushSetReqDto;
import kr.co.fittnerserver.dto.user.myPage.response.*;
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
@Tag(name = "마이페이지", description = "마이페이지 내 기능들입니다.")
public class MypageController {

    final MyPageService myPageService;

    @Operation(summary = "수익관리 요약 조회 API", description = "수익관리 요약 API 입니다.",operationId = "getUserMyPageSalesInfo")
    @GetMapping("/myPage/sales-info")
    public ResponseEntity<ApiResponseMessage<SalesInfoResDto>> getSalesInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.build(myPageService.getSalesInfo(customUserDetails));
    }

    @Operation(summary = "수익관리 조회 API", description = "수익관리 조회 API 입니다.",operationId = "getUserMyPageSalesReservationStartMonth")
    @GetMapping("/myPage/sales/{reservationStartMonth}")
    public ResponseEntity<ApiResponseMessage<List<SalesResDto>>> getSales(@Parameter(name = "reservationStartMonth", description = "예약시작월", example = "202501")
                                                                    @PathVariable(name = "reservationStartMonth") String reservationStartMonth,
                                                                          @ModelAttribute FittnerPageable pageable,
                                                                          @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(myPageService.getSales(reservationStartMonth,customUserDetails,pageable));
    }

    @Operation(summary = "수익관리 상세 조회 API", description = "수익관리 상세 조회 API 입니다.",operationId = "getUserMyPageSalesDetail")
    @GetMapping("/myPage/sales/detail")
    public ResponseEntity<ApiResponseMessage<List<SalesDetailResDto>>> getSalesDetail(@Parameter(name = "reservationStartMonth", description = "예약시작월", example = "202501")
                                                                                      @RequestParam(value = "reservationStartMonth") String reservationStartMonth,
                                                                                      @Parameter(description = "티켓ID", example = "dfb99734-ccd9-11ef-b7c9-0242ac190002")
                                                                                      @RequestParam(value = "ticketId") String ticketId,
                                                                                      @ModelAttribute FittnerPageable pageable,
                                                                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(myPageService.getSalesDetail(reservationStartMonth,ticketId,customUserDetails,pageable));
    }

    @Operation(summary = "공지사항 조회 API", description = "공지사항 조회 API 입니다.",operationId = "getUserMyPageNotices")
    @GetMapping("/myPage/notices")
    public ResponseEntity<ApiResponseMessage<List<NoticeResDto>>> getNotices(@ModelAttribute FittnerPageable pageable, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(myPageService.getNotices(customUserDetails,pageable));
    }

    @Operation(summary = "공지사항 읽음 API", description = "공지사항 읽음 API 입니다.",operationId = "postUserMyPageNoticeRead")
    @PostMapping("/myPage/notice/read")
    public ResponseEntity<ApiResponseMessage<Object>> noticeRead(@RequestBody NoticeReadReqDto noticeReadReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        myPageService.noticeRead(noticeReadReqDto,customUserDetails);
        return FittnerResponse.ok();
    }

    @Operation(summary = "약관 조회 API", description = "약관 조회 API 입니다.",operationId = "getUserMyPageTerms")
    @GetMapping("/myPage/terms")
    public ResponseEntity<ApiResponseMessage<List<TermsListResDto>>> getTerms(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(myPageService.getTerms(customUserDetails));
    }

    @Operation(summary = "푸시 설정 API", description = "푸시 설정 API 입니다.",operationId = "postUserMyPagePushSet")
    @PostMapping("/myPage/push-set")
    public ResponseEntity<ApiResponseMessage<Object>> setPush(@RequestBody PushSetReqDto pushSetReqDto,  @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        myPageService.setPush(pushSetReqDto, customUserDetails);
        return FittnerResponse.ok();
    }

    @Operation(summary = "푸시 설정 조회 API", description = "푸시 설정 조회 API 입니다.",operationId = "postUserMyPagePush")
    @PostMapping("/myPage/push")
    public ResponseEntity<ApiResponseMessage<List<PushSetResDto>>> getPush(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(myPageService.getPush(customUserDetails));
    }

    @Operation(summary = "마이페이 기본 정보 조회 API", description = "마이페이지 기본 정보 조회 API 입니다.",operationId = "getUserMyPageDefaultInfo")
    @GetMapping("/myPage/default-info")
    public ResponseEntity<ApiResponseMessage<MyPageInfoResDto>> myPageInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.build(myPageService.myPageInfo(customUserDetails));
    }
}
