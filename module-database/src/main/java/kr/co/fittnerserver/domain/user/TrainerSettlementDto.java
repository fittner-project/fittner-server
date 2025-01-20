package kr.co.fittnerserver.domain.user;

import lombok.*;

@Data
public class TrainerSettlementDto {

    private String trainerSettlementId;
    private String trainerSettlementPercent;
    private String trainerSettlementCode;
    private String centerId;
    private String trainerId;

}
