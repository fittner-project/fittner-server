package kr.co.fittnerserver.dto.user.sign.response;

import lombok.Data;

import java.util.List;

@Data
public class SignResrvationResDto {

    private String reservationTotalCnt;

    private List<SignResrvationDto> reservationList;

}
