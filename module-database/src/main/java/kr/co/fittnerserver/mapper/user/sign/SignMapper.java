package kr.co.fittnerserver.mapper.user.sign;

import kr.co.fittnerserver.dto.user.sign.response.SignResrvationDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationForMemberResDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SignMapper {

    List<SignResrvationDto> selectReservationByTrainerId(String trainerId, String reservationStartDate);

    SignResrvationForMemberResDto selectReservationByTicketId(String trainerId, String ticketId, int currentPageNo);
}
