package kr.co.fittnerserver.controller.user.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationDeleteReqDto;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationReqDto;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationSearchDto;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationUpdateReqDto;
import kr.co.fittnerserver.dto.user.reservation.response.GroupedReservationMemberDto;
import kr.co.fittnerserver.dto.user.reservation.response.ReservationColorResDto;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.service.user.reservation.ReservationService;
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
@Tag(name = "수업", description = "수업 관련 처리입니다.")
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "트레이너가 수업 등록할때 필요한 색상 리스트 API", description = "트레이너가 수업 등록할때 필요한 색상 리스트 API입니다.",operationId = "getUserReservationColors")
    @GetMapping("/reservation/colors")
    public ResponseEntity<ApiResponseMessage<ReservationColorResDto>> getColors() throws Exception {
        return FittnerResponse.build(reservationService.getColors());

    }

    @Operation(summary = "트레이너가 수업 등록하는 API", description = "트레이너가 수업을 등록하는 API 입니다.",operationId = "postUserReservation")
    @PostMapping("/reservation")
    public ResponseEntity<ApiResponseMessage<Object>> reservation(@RequestBody ReservationReqDto reservationReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        reservationService.reservation(reservationReqDto, customUserDetails);
        return FittnerResponse.ok();
    }

    @Operation(summary = "트레이너의 회원 수업 목록 리스트 API", description = "트레이너의 회원 수업 목록 리스트 API 입니다.",operationId = "getUserReservations")
    @GetMapping("/reservations")
    public ResponseEntity<ApiResponseMessage<List<GroupedReservationMemberDto>>> getReservations(@ModelAttribute ReservationSearchDto reservationSearchDto,
                                                                                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(reservationService.getReservations(reservationSearchDto, customUserDetails));
    }

    @Operation(summary = "트레이너 수업 디테일 API", description = "트레이너가 수업 디테일 조회하는 API 입니다.",operationId = "getUserReservationDetail")
    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<ApiResponseMessage<Object>> reservationDetail(@PathVariable(value = "reservationId") String reservationId, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.build(reservationService.getReservationDetail(reservationId, customUserDetails));
    }

    @Operation(summary = "트레이너가 수업 변경하는 API", description = "트레이너가 수업을 변경하는 API 입니다.",operationId = "putUserReservationUpadete")
    @PutMapping("/reservation")
    public ResponseEntity<ApiResponseMessage<Object>> reservationUpadete(@RequestBody ReservationUpdateReqDto reservationUpdateReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        reservationService.reservationUpadete(reservationUpdateReqDto, customUserDetails);
        return FittnerResponse.ok();
    }

    @Operation(summary = "트레이너가 수업 삭제하는 API", description = "트레이너가 수업을 삭제하는 API 입니다.",operationId = "deleteUserReservationDelete")
    @DeleteMapping("/reservation")
    public ResponseEntity<ApiResponseMessage<Object>> reservationDelete(@RequestBody ReservationDeleteReqDto reservationDeleteReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        reservationService.reservationDelete(reservationDeleteReqDto, customUserDetails);
        return FittnerResponse.ok();
    }

}
