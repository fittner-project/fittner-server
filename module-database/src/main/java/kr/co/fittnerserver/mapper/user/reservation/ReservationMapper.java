package kr.co.fittnerserver.mapper.user.reservation;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ReservationMapper {

    int selectReservationForCount(String ticketId);
}
