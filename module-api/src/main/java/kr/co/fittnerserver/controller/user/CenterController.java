package kr.co.fittnerserver.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.fittnerserver.dto.user.CenterListResDto;
import kr.co.fittnerserver.results.ApiResponseMessage;
import kr.co.fittnerserver.results.FittnerResponse;
import kr.co.fittnerserver.service.user.CenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/center")
@RequiredArgsConstructor
@Slf4j
public class CenterController {

    private final CenterService centerService;

    @Operation(summary = "센터 목록 조회 API", description = "센터 목록 조회 API 입니다.")
    @GetMapping("/list")
    public ResponseEntity<ApiResponseMessage<List<CenterListResDto>>> centerList() throws Exception {
        return FittnerResponse.build(centerService.getCenterList());
    }

}
