package kr.co.fittnerserver.controller.user.push;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.push.request.PushReadReqDto;
import kr.co.fittnerserver.dto.user.push.response.PushChkResDto;
import kr.co.fittnerserver.dto.user.push.response.PushResDto;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.service.user.push.PushService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kr.co.fittnerserver.util.FcmTokenUtil.makeMultiMessages;


@RestController
@RequestMapping(value = "/api/v1/push")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "푸시", description = "푸시 알람입니다.")
public class PushController {

    final PushService pushService;

    @Operation(summary = "신규 알림 여부 조회 API", description = "신규 알림이 있는지(안읽은 알림) 확인하는 API 입니다.",operationId = "getUserPushChk")
    @GetMapping("/chk")
    public ResponseEntity<ApiResponseMessage<PushChkResDto>> pushChk(@Parameter(description = "센터ID", example = "1")
                                                                     @RequestParam(value = "centerId") String centerId,
                                                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.build(pushService.pushChk(centerId, customUserDetails));
    }

    @Operation(summary = "알림 리스트 조회 API", description = "알림 리스트 조회 API 입니다.",operationId = "getUserPushs")
    @GetMapping("/pushs")
    public ResponseEntity<ApiResponseMessage<List<PushResDto>>> getPushs(@Parameter(description = "센터ID", example = "1")
                                                                         @RequestParam(value = "centerId") String centerId,
                                                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return FittnerResponse.buildList(pushService.getPushs(centerId, customUserDetails));
    }

    @Operation(summary = "알림 읽음 API", description = "알림 읽음 API 입니다.",operationId = "postUserPushRead")
    @PostMapping("/read")
    public ResponseEntity<ApiResponseMessage<Object>> pushRead(@RequestBody PushReadReqDto pushReadReqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        pushService.pushRead(pushReadReqDto, customUserDetails);
        return FittnerResponse.ok();
    }

    @PostMapping("/send")
    public ResponseEntity<ApiResponseMessage<Object>> test() throws FirebaseMessagingException {
        List<MulticastMessage> multicastMessages = makeMultiMessages("제목", "내용", List.of("f9Hn-OG_NkUEog_Qb13UpQ:APA91bE702H25lHW2839iFZSlgRYBwFrrx7hLQfjgiwueujK6vnXxpsUaV4D1-cAoInRxo-TC8dYC8iaBW6thJ9Mb4GRCTm8ZueJ6k5TuOCUm4NHf8NcQGY"),1);
        pushService.sendPush(multicastMessages);
        return FittnerResponse.ok();
    }
}
