package kr.co.fittnerserver.domain.user;

import lombok.*;

@Data
public class TrainerDto {

    private String trainerId;
    private String trainerPhone;
    private String trainerName;
    private String trainerStatus;
    private String trainerEmail;
    private String trainerSnsKind;
    private String trainerCiNo;
    private String trainerDropYn;
    private String trainerFcmToken;
    private String trainerProductChangeYn;
    private String centerId;
}
