package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.dto.user.reservation.request.ReservationSearchDto;
import kr.co.fittnerserver.dto.user.reservation.response.MainShortsReservationResDto;
import kr.co.fittnerserver.dto.user.reservation.response.ReservationMemberResDto;
import kr.co.fittnerserver.entity.user.Reservation;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,String> {


    @Query(value = """
              SELECT new kr.co.fittnerserver.dto.user.reservation.response.ReservationMemberResDto(
                    RIGHT(r.reservationStartDate, 2),
                    m.memberName,
                    r.reservationStartDate,
                    r.reservationEndDate,
                    r.reservationStartTime,
                    r.reservationEndTime,
                    CAST(r.reservationColor AS string),
                    r.reservationMemo,
                    tk.ticketUseCnt + 1
              )
              FROM Reservation r
              LEFT JOIN Trainer t
              ON r.trainer.trainerId = t.trainerId
              LEFT JOIN Member m
              ON r.trainer.trainerId = m.trainer.trainerId
              LEFT JOIN Ticket tk
              ON r.trainer.trainerId = tk.trainer.trainerId
              WHERE m.memberDeleteYn = 'N'
              AND r.reservationDeleteYn = 'N'
              AND t.trainerStatus != 'DROP'
              AND r.trainer.trainerId = :trainerId
              AND r.reservationStartDate >= :reservationStartDate
              AND r.reservationEndDate <= :reservationEndDate
              ORDER BY RIGHT(r.reservationStartDate, 2) ASC
""")
    List<ReservationMemberResDto> getReservationMemberDataList(@Param(value = "reservationStartDate") String reservationStartDate,
                                                                @Param(value = "reservationEndDate") String reservationEndDate,
                                                               @Param(value = "trainerId") String trainerId);

    @Query(value = """
              SELECT new kr.co.fittnerserver.dto.user.reservation.response.MainShortsReservationResDto(
                    RIGHT(r.reservationStartDate, 2),
                    CAST(r.reservationColor AS string)
              )
              FROM Reservation r
              LEFT JOIN Trainer t
              ON r.trainer.trainerId = t.trainerId
              LEFT JOIN Member m
              ON r.trainer.trainerId = m.trainer.trainerId
              LEFT JOIN Ticket tk
              ON r.trainer.trainerId = tk.trainer.trainerId
              WHERE m.memberDeleteYn = 'N'
              AND r.reservationDeleteYn = 'N'
              AND t.trainerStatus != 'DROP'
              AND r.trainer.trainerId = :trainerId
              AND r.reservationStartDate >= :reservationStartDate
              AND r.reservationEndDate <= :reservationEndDate
              ORDER BY RIGHT(r.reservationStartDate, 2) ASC
""")
    List<MainShortsReservationResDto> getMainShortSchedules(String reservationStartDate, String reservationEndDate, String trainerId);
}
