package kr.co.fittnerserver.controller.user.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationReqDto;
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

    @Operation(summary = "트레이너가 수업 등록할때 필요한 색상 리스트 API", description = "트레이너가 수업 등록할때 필요한 색상 리스트 API입니다.")
    @GetMapping("/reservation/colors")
    public ResponseEntity<ApiResponseMessage<ReservationColorResDto>> getColors() throws Exception {
        return FittnerResponse.build(reservationService.getColors());

    }

    @Operation(summary = "트레이너가 수업 등록하는 API", description = "트레이너가 수업을 등록하는 API 입니다.")
    @PostMapping("/reservation")
    public ResponseEntity<ApiResponseMessage<Object>> reservation(@RequestBody ReservationReqDto reservationReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        reservationService.reservation(reservationReqDto, customUserDetails);
        return FittnerResponse.ok();
    }

    //@Operation(summary = "트레이너의 회원 수업 목록 리스트 API", description = "트레이너의 회원 수업 목록 리스트 API 입니다.")

}
