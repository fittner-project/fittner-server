package kr.co.fittnerserver.repository.user;

import kr.co.fittnerserver.dto.user.reservation.response.MainReservationsResDto;
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
                    tk.ticketUseCnt + 1,
                    tp.trainerProductCount
              )
              FROM Reservation r
              LEFT JOIN Trainer t
              ON r.trainer.trainerId = t.trainerId
              LEFT JOIN Ticket tk
              ON r.ticket.ticketId = tk.ticketId
              LEFT JOIN Member m
              ON r.member.memberId = m.memberId
              LEFT JOIN TrainerProduct tp
              on r.trainer.trainerId = tp.trainer.trainerId
              WHERE m.memberDeleteYn = 'N'
              AND r.reservationDeleteYn = 'N'
              AND t.trainerStatus != 'DROP'
              AND t.trainerId = :trainerId
              AND r.reservationStartDate >= :reservationStartDate
              AND r.reservationEndDate <= :reservationEndDate
              ORDER BY RIGHT(r.reservationStartDate, 2) ASC,r.reservationStartDate ASC, r.reservationStartTime ASC
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
              LEFT JOIN Ticket tk
              ON r.ticket.ticketId = tk.ticketId
              LEFT JOIN Member m
              ON r.member.memberId = m.memberId
              WHERE m.memberDeleteYn = 'N'
              AND r.reservationDeleteYn = 'N'
              AND t.trainerStatus != 'DROP'
              AND r.trainer.trainerId = :trainerId
              AND r.reservationStartDate >= :reservationStartDate
              AND r.reservationEndDate <= :reservationEndDate
              ORDER BY RIGHT(r.reservationStartDate, 2) ASC
""")
    List<MainShortsReservationResDto> getMainShortSchedules(String reservationStartDate, String reservationEndDate, String trainerId);


    @Query(value = """
        SELECT new kr.co.fittnerserver.dto.user.reservation.response.MainReservationsResDto(
            RIGHT(r.reservationStartDate, 4),
            CAST(r.reservationColor AS string),
            m.memberName,
            r.reservationStartDate,
            r.reservationEndDate,
            r.reservationStartTime,
            r.reservationEndTime,
            tp.trainerProductCount,
            tk.ticketUseCnt
        )
        FROM Reservation r
        LEFT JOIN Trainer t
        ON r.trainer.trainerId = t.trainerId
        LEFT JOIN Ticket tk
        ON r.ticket.ticketId = tk.ticketId
        LEFT JOIN TrainerProduct tp
        ON tp.trainerProductId = tk.trainerProduct.trainerProductId
        LEFT JOIN Member m
        ON r.member.memberId = m.memberId
        WHERE m.memberDeleteYn = 'N'
        AND r.reservationDeleteYn = 'N'
        AND t.trainerStatus != 'DROP'
        AND t.trainerId = :trainerId
        AND r.reservationStartDate = :startDate
        ORDER BY RIGHT(r.reservationStartDate, 4) asc , r.reservationStartDate ASC, r.reservationStartTime ASC
""")
    List<MainReservationsResDto> getMainSchedules(@Param(value = "trainerId") String trainerId,
                                                  @Param(value = "startDate") String startDate);
}
