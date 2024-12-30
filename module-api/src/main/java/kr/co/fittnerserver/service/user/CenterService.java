package kr.co.fittnerserver.service.user;


import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.dto.user.CenterListResDto;
import kr.co.fittnerserver.dto.user.JoinReqDto;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.repository.common.CenterRepository;
import kr.co.fittnerserver.repository.user.TrainerRepository;
import kr.co.fittnerserver.util.AES256Cipher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CenterService {

    private final CenterRepository centerRepository;


    @Transactional(readOnly = true)
    public List<CenterListResDto> getCenterList() {
        return centerRepository.findAllByCenterDeleteYn("N")
                .stream()
                .map(center -> CenterListResDto.builder()
                        .centerId(center.getCenterId())
                        .centerName(center.getCenterName())
                        .centerAddress(center.getCenterAddress())
                        .centerTel(center.getCenterTel())
                        .centerImage("")
                        .build())
                .collect(Collectors.toList());
    }
}
