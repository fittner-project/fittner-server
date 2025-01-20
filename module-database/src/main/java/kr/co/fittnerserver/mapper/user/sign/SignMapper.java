package kr.co.fittnerserver.mapper.user.sign;

import kr.co.fittnerserver.dto.user.sign.request.SignReqDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationForMemberResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SignMapper {

    int selectReservationByTrainerIdCnt(String trainerId, String reservationStartDate);
    List<SignResrvationDto> selectReservationByTrainerId(String trainerId, String reservationStartDate, int currentPageNo);

    SignResrvationForMemberResDto selectReservationByTicketId(String trainerId, String ticketId, int currentPageNo);

    void insertSign(@Param("signReqDto") SignReqDto signReqDto, @Param("trainerId") String trainerId);
}
