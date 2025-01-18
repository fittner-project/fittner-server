package kr.co.fittnerserver.domain.user;

import lombok.*;

@Data
public class TrainerProductDto {

    private String trainerProductId;
    private String trainerProductName;
    private String trainerProductCount;
    private String trainerProductPrice;
    private String trainerProductDeleteYn;
    private String centerId;
    private String trainerId;
    private String memberId;
}
