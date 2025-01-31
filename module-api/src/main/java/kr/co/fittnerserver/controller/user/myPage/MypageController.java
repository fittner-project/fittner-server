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
import org.springdoc.core.annotations.ParameterObject;
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

    @Operation(summary = "수익관리 헤더 조회 API", description = "수익관리 헤더 조회 API 입니다.", operationId = "getUserMyPageSales")
    @GetMapping("/myPage/sales")
    public ResponseEntity<ApiResponseMessage<SalesResDto>> getSales(@Parameter(description = "센터ID", example = "1")
                                                                    @RequestParam(value = "centerId") String centerId,
                                                                    @Parameter(description = "예약시작월", example = "202501")
                                                                    @RequestParam(value = "reservationStartMonth", required = false) String reservationStartMonth,
                                                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.build(myPageService.getSales(centerId, reservationStartMonth, customUserDetails));
    }

    @Operation(summary = "수익관리 바디 조회 API", description = "수익관리 바디 조회 API 입니다.", operationId = "getUserMyPageSalesInfo")
    @GetMapping("/myPage/sales/info")
    public ResponseEntity<ApiResponseMessage<List<SalesInfoResDto>>> getSalesInfo(@Parameter(name = "reservationStartMonth", description = "예약시작월", example = "202501")
                                                                                  @RequestParam(name = "reservationStartMonth") String reservationStartMonth,
                                                                                  @Parameter(description = "센터ID", example = "1")
                                                                                  @RequestParam(value = "centerId") String centerId,
                                                                                  @ParameterObject FittnerPageable pageable,
                                                                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(myPageService.getSalesInfo(centerId,reservationStartMonth,customUserDetails,pageable));
    }

    @Operation(summary = "수익관리 바디 상세 조회 API", description = "수익관리 바디 상세 조회 API 입니다.", operationId = "getUserMyPageSalesInfoDetail")
    @GetMapping("/myPage/sales/info/detail")
    public ResponseEntity<ApiResponseMessage<List<SalesInfoDetailResDto>>> getSalesInfoDetail(@Parameter(name = "reservationStartMonth", description = "예약시작월(202501 or TOTAL)", example = "202501")
                                                                                              @RequestParam(value = "reservationStartMonth") String reservationStartMonth,
                                                                                              @Parameter(description = "티켓ID", example = "dfb99734-ccd9-11ef-b7c9-0242ac190002")
                                                                                              @RequestParam(value = "ticketId") String ticketId,
                                                                                              @ParameterObject FittnerPageable pageable,
                                                                                              @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(myPageService.getSalesInfoDetail(reservationStartMonth,ticketId,customUserDetails,pageable));
    }

    @Operation(summary = "공지사항 조회 API", description = "공지사항 조회 API 입니다.",operationId = "getUserMyPageNotices")
    @GetMapping("/myPage/notices")
    public ResponseEntity<ApiResponseMessage<List<NoticeResDto>>> getNotices(@Parameter(description = "센터ID", example = "1")
                                                                             @RequestParam(value = "centerId") String centerId,
                                                                             @ParameterObject FittnerPageable pageable,
                                                                             @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(myPageService.getNotices(centerId,customUserDetails,pageable));
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
