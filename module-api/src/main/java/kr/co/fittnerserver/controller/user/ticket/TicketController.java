package kr.co.fittnerserver.controller.user.ticket;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.ticket.request.*;
import kr.co.fittnerserver.dto.user.ticket.response.AssignToInfoResDto;
import kr.co.fittnerserver.dto.user.ticket.response.RefundInfoResDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketDetailResDto;
import kr.co.fittnerserver.dto.user.ticket.response.TicketListResDto;
import kr.co.fittnerserver.results.ApiResponseMessage;
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
    public ResponseEntity<ApiResponseMessage<List<TicketListResDto>>> getTickets(@Parameter(name = "ticketCode", description = "이용권 코드(TOTAL | ING | STOP | ASSIGN_TO | ASSIGN_FROM | REFUND | AFTER)", example = "TOTAL")
                                                                                 @RequestParam(value = "ticketCode") String ticketCode,
                                                                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception{
        return FittnerResponse.buildList(ticketService.getTickets(ticketCode, customUserDetails));
    }

    @Operation(summary = "이용권 상세 조회 API", description = "이용권 상세 조회 API 입니다.",operationId = "getUserTicketTicketId")
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<ApiResponseMessage<TicketDetailResDto>> getTicketDetail(@Parameter(name = "ticketId", description = "이용권ID", example = "03fa7063-d6e2-11ef-b7c9-0242ac190002")
                                                                                  @PathVariable(name = "ticketId") String ticketId,
                                                                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception{
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

    @Operation(summary = "이용권 추가등록 API", description = "이용권 추가등록 API 입니다.",operationId = "postUserTicketPlus")
    @PostMapping("/ticket/plus")
    public ResponseEntity<ApiResponseMessage<Object>> ticketPlus(@RequestBody PlusReqDto plusReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception{
        ticketService.ticketPlus(plusReqDto, customUserDetails);
        return FittnerResponse.ok();
    }

    @Operation(summary = "이용권 환불 정보 조회 API", description = "이용권 환불 정보 조회 API 입니다.",operationId = "getUserTicketRefundInfo")
    @GetMapping("/ticket/refund-info")
    public ResponseEntity<ApiResponseMessage<RefundInfoResDto>> ticketRefundInfo(@Parameter(name = "ticketId", description = "티켓ID", example = "ec4b8eb6-d01e-11ef-b7c9-0242ac190002")
                                                                                 @RequestParam(name = "ticketId") String ticketId,
                                                                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception{
        return FittnerResponse.build(ticketService.ticketRefundInfo(ticketId, customUserDetails));
    }

    @Operation(summary = "이용권 환불 요청 API", description = "이용권 환불 요청 API 입니다.",operationId = "postUserTicketRefundAllow")
    @PostMapping("/ticket/refund-allow")
    public ResponseEntity<ApiResponseMessage<Object>> ticketRefund(@RequestBody RefundReqDto refundReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception{
        ticketService.ticketRefund(refundReqDto, customUserDetails);
        return FittnerResponse.ok();
    }

    @Operation(summary = "티켓 일시정지 요청 API", description = "티켓 일시정지 요청 API 입니다.",operationId = "postUserTicketSuspendAllow")
    @PostMapping("/ticket/suspend-allow")
    public ResponseEntity<ApiResponseMessage<Object>> suspendTicket(@RequestBody SuspendTicketReqDto suspendTicketReqDto) throws Exception {
        ticketService.suspendMemberTicket(suspendTicketReqDto);
        return FittnerResponse.ok();
    }

    @Operation(summary = "티켓 일시정지 해제 API", description = "티켓 일시정지 해제 API 입니다.",operationId = "putUserTicketAgainstSuspend")
    @PutMapping("/ticket/against/suspend/{ticketId}")
    public ResponseEntity<ApiResponseMessage<Object>> againstSuspendTicket(@PathVariable("ticketId") String ticketId) throws Exception {
        ticketService.againstSuspendMemberTicket(ticketId);
        return FittnerResponse.ok();
    }
}
