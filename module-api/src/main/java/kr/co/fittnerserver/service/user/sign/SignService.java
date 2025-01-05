package kr.co.fittnerserver.service.user.sign;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationResDto;
import kr.co.fittnerserver.mapper.user.sign.SignMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignService {

    private final SignMapper signMapper;

    public SignResrvationResDto getReservations(CustomUserDetails customUserDetails) throws Exception {

        SignResrvationResDto r = new SignResrvationResDto();

        List<SignResrvationDto> reservationDtoList = signMapper.selectReservationByTrainerId(customUserDetails.getTrainerId(), "");

        r.setReservationTotalCnt(String.valueOf(reservationDtoList.size()));
        r.setReservationList(reservationDtoList);

        return r;
    }

}
