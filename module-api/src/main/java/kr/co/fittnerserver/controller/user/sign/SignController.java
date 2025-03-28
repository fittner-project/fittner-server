package kr.co.fittnerserver.controller.user.sign;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.file.response.FileResDto;
import kr.co.fittnerserver.dto.user.sign.request.SignReqDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationForMemberResDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationResDto;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.service.user.sign.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "서명", description = "서명 및 노쇼 처리입니다.")
public class SignController {

    private final SignService signService;

    @Operation(summary = "서명전 날짜별 예약 리스트 조회 API", description = "서명전 날짜별 예약 리스트 조회 API 입니다.", operationId = "getUserSignReservations")
    @GetMapping("/sign/reservations")
    public ResponseEntity<ApiResponseMessage<SignResrvationResDto>> getReservations(@Parameter(description = "예약 시작 일자", example = "20250101")
                                                                                    @RequestParam(value = "reservationStartDate") String reservationStartDate,
                                                                                    @Parameter(description = "센터ID", example = "1")
                                                                                    @RequestParam(value = "centerId") String centerId,
                                                                                    @ParameterObject FittnerPageable pageable,
                                                                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.build(signService.getReservations(reservationStartDate,centerId,pageable,customUserDetails));
    }

    @Operation(summary = "서명할 예약 리스트 조회 API", description = "서명할 예약 리스트 조회 API 입니다.", operationId = "getUserSignReservationsTicketId")
    @GetMapping("/sign/reservations/{ticketId}")
    public ResponseEntity<ApiResponseMessage<List<SignResrvationForMemberResDto>>> getReservationsForMember(@Parameter(description = "이용권ID", example = "55531c95-cb79-11ef-b7c9-0242ac190002")
                                                                                                            @PathVariable(name = "ticketId") String ticketId,
                                                                                                            @ParameterObject FittnerPageable pageable,
                                                                                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(signService.getReservationsForMember(ticketId,customUserDetails,pageable));
    }

    @Operation(summary = "서명 요청 API", description = "서명 요청 API 입니다.", operationId = "postUserSign")
    @PostMapping("/sign")
    public ResponseEntity<ApiResponseMessage<Object>> sign(@RequestBody SignReqDto signReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        signService.reservationSign(signReqDto,customUserDetails);
        return FittnerResponse.ok();
    }
}
