package kr.co.fittnerserver.controller.user.ticket;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.ticket.request.AssignToNewMemberReqDto;
import kr.co.fittnerserver.dto.user.ticket.request.AssignToOldMemberReqDto;
import kr.co.fittnerserver.dto.user.ticket.request.RelayReqDto;
import kr.co.fittnerserver.dto.user.ticket.response.AssignToInfoResDto;
import kr.co.fittnerserver.dto.user.ticket.response.RefundInfoResDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketDetailResDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketListResDto;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.service.user.ticket.TicketService;
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

    final TicketService ticketService;

    @Operation(summary = "전체 이용권 리스트 조회 API", description = "전체 이용권 리스트 조회 API 입니다.", operationId = "getUserTickets")
    @GetMapping("/tickets")
    public ResponseEntity<ApiResponseMessage<List<TicketListResDto>>> getTickets(@Parameter(name = "ticketStatus", description = "티켓상태", example = "TOTAL|ING|STOP|ASSIGN_TO|ASSIGN_FROM|REFUND|BEFORE|AFTER")
                                                                                 @RequestParam(value = "ticketStatus") String ticketStatus,
                                                                                 @ModelAttribute FittnerPageable pageable,
                                                                                 @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return FittnerResponse.buildList(ticketService.getTickets(ticketStatus, pageable, customUserDetails));
    }

    @Operation(summary = "이용권 상세 조회 API", description = "이용권 상세 조회 API 입니다.",operationId = "getUserTicketTicketId")
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<ApiResponseMessage<TicketDetailResDto>> getTicketDetail(@Parameter(name = "ticketId", description = "티켓ID", example = "ec4b8eb6-d01e-11ef-b7c9-0242ac190002")
                                                                                  @RequestParam(name = "ticketId") String ticketId,
                                                                                  @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return FittnerResponse.build(ticketService.getTicketDetail(ticketId, customUserDetails));
    }

    @Operation(summary = "이용권 양도 정보 조회 API", description = "이용권 양도 정보 조회 API 입니다.",operationId = "getUserTicketAssignInfo")
    @GetMapping("/ticket/assign-info")
    public ResponseEntity<ApiResponseMessage<AssignToInfoResDto>> ticketAssignToInfo(@Parameter(name = "ticketId", description = "티켓ID", example = "ec4b8eb6-d01e-11ef-b7c9-0242ac190002")
                                                                                     @RequestParam(name = "ticketId") String ticketId,
                                                                                     @Parameter(name = "memberId", description = "회원ID", example = "ec4b8eb6-d01e-11ef-b7c9-0242ac190002")
                                                                                     @RequestParam(name = "memberId") String memberId,
                                                                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception{
        return FittnerResponse.build(ticketService.ticketAssignToInfo(ticketId, memberId, customUserDetails));
    }

    @Operation(summary = "이용권 기존 회원 양도 API", description = "이용권 기존 회원 양도 API 입니다.",operationId = "postUserTicketAssignOldMember")
    @PostMapping("/ticket/assign/old-member")
    public ResponseEntity<ApiResponseMessage<Object>> ticketAssignToOldMember(@RequestBody AssignToOldMemberReqDto assignToOldMemberReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception{
        ticketService.ticketAssignToOldMember(assignToOldMemberReqDto, customUserDetails);
        return FittnerResponse.ok();
    }

    @Operation(summary = "이용권 신규 회원 양도 API", description = "이용권 신규 회원 양도 API 입니다.",operationId = "postUserTicketAssignNewMember")
    @PostMapping("/ticket/assign/new-member")
    public ResponseEntity<ApiResponseMessage<Object>> ticketAssignToNewMember(@RequestBody AssignToNewMemberReqDto assignToNewMemberReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception{
        ticketService.ticketAssignToNewMember(assignToNewMemberReqDto, customUserDetails);
        return FittnerResponse.ok();
    }

    @Operation(summary = "이용권 연장하기 API", description = "이용권 연장하기 API 입니다.",operationId = "postUserTicketRelay")
    @PostMapping("/ticket/relay")
    public ResponseEntity<ApiResponseMessage<Object>> ticketRelay(@RequestBody RelayReqDto relayReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception{
        ticketService.ticketRelay(relayReqDto, customUserDetails);
        return FittnerResponse.ok();
    }

    @Operation(summary = "이용권 환불 정보 조회 API", description = "이용권 환불 정보 조회 API 입니다.",operationId = "getUserTicketRefundInfo")
    @GetMapping("/ticket/refund-info")
    public ResponseEntity<ApiResponseMessage<RefundInfoResDto>> ticketRefundInfo(@Parameter(name = "ticketId", description = "티켓ID", example = "ec4b8eb6-d01e-11ef-b7c9-0242ac190002")
                                                                                 @RequestParam(name = "ticketId") String ticketId,
                                                                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception{
        return FittnerResponse.build(ticketService.ticketRefundInfo(ticketId, customUserDetails));
    }
}
