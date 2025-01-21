package kr.co.fittnerserver.mapper.user.reservation;

import kr.co.fittnerserver.domain.user.ReservationDto;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ReservationMapper {

    int selectReservationForCount(String ticketId);

    ReservationDto selectReservationByReservationId(String reservationId);

    void updateReservationForSign(String reservationId, String reservationStatus, String trainerId);

    void updateReservationForSettlement(String reservationId, String trainerId, String settlementPrice, String trainerSettlementId);

}
