package kr.co.fittnerserver.controller.user.ticket;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.ticket.request.TicketListReqDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketDetailResDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketListResDto;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.service.user.ticket.TIcketService;
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
@Tag(name = "이용권", description = "이용권(티켓) 처리입니다.")
public class TicketController {

    final TIcketService tIcketService;

    @Operation(summary = "전체 이용권 리스트 조회 API", description = "전체 이용권 리스트 조회 API 입니다.")
    @GetMapping("/tickets")
    public ResponseEntity<ApiResponseMessage<List<TicketListResDto>>> getTickets(@RequestBody TicketListReqDto ticketListReqDto,
                                                                                 @ModelAttribute FittnerPageable pageable,
                                                                                 @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return FittnerResponse.buildList(tIcketService.getTickets(ticketListReqDto, pageable, customUserDetails));
    }

    @Operation(summary = "이용권 상세 조회 API", description = "이용권 상세 조회 API 입니다.")
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<ApiResponseMessage<TicketDetailResDto>> getTicketDetail(@Parameter(name = "ticketId", description = "티켓ID", example = "ec4b8eb6-d01e-11ef-b7c9-0242ac190002")
                                                                                  @PathVariable(name = "ticketId") String ticketId,
                                                                                  @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return FittnerResponse.build(tIcketService.getTicketDetail(ticketId, customUserDetails));
    }
}
