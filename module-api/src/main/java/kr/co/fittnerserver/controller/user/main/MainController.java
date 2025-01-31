package kr.co.fittnerserver.controller.user.main;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.reservation.request.ReservationSearchDto;
import kr.co.fittnerserver.dto.user.reservation.response.GroupedReservationDto;
import kr.co.fittnerserver.dto.user.reservation.response.MainReservationsResDto;
import kr.co.fittnerserver.dto.user.user.response.MainUserCenterListResDto;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.results.PageResponseDto;
import kr.co.fittnerserver.service.user.main.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "메인페이지", description = "메인페이지 내 기능들입니다.")
public class MainController {

    private final MainService mainService;

    @Operation(summary = "메인에서 트레이너가 지정한 센터 목록 조회 API", description = "트레이너가 지정한 센터 목록 조회 API 입니다.", operationId = "getUserMainCenters")
    @GetMapping("/main/centers")
    public ResponseEntity<ApiResponseMessage<PageResponseDto<MainUserCenterListResDto>>> mainCenterList(@ParameterObject FittnerPageable pageable, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildPage(mainService.getMainCenterListByTrainer(customUserDetails, pageable.getPageable()), pageable);
    }

    @Operation(summary = "메인페이지 일정 간략보기 데이터 조회 API", description = "메인페이지의 일정 간략보기 데이터 조회 API 입니다.", operationId = "getUserMainShortSchedules")
    @GetMapping("/main/short-schedules")
    public ResponseEntity<ApiResponseMessage<List<GroupedReservationDto>>> mainShortSchedules(@ModelAttribute ReservationSearchDto reservationSearchDto,
                                                                                              @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(mainService.getMainShortSchedules(reservationSearchDto, customUserDetails));
    }

    @Operation(summary = "메인페이지 상단 수업 표출 단건조회 API", description = "메인페이지 상단 수업 표출 단건조회 API 입니다.", operationId = "getUserMainSchedule")
    @GetMapping("/main/schedule")
    public ResponseEntity<ApiResponseMessage<MainReservationsResDto>> mainSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.build(mainService.getMainSchedule(customUserDetails));
    }
}
