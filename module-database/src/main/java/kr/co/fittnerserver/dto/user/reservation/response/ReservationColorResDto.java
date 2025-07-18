package kr.co.fittnerserver.dto.user.reservation.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationColorResDto {
    private List<Color> colors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Color{
        String colorHex;
        String colorName;
    }
}
