package kr.co.fittnerserver.entity.user.enums;

import kr.co.fittnerserver.dto.user.reservation.response.ReservationColorResDto;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum ReservationColor {
    COLOR_007AFF("007AFF", "블루"),
    COLOR_34C759("34C759", "그린"),
    COLOR_FFD220("FFD220", "옐로우"),
    COLOR_FF3B30("FF3B30", "레드"),
    COLOR_7AC6F5("7AC6F5", "스카이블루"),
    COLOR_AF52DE("AF52DE", "퍼플"),
    COLOR_5856D6("5856D6", "바이올렛"),
    COLOR_FF63C1("FF63C1", "핑크")
    ;

    private final String colorCode;
    private final String colorName;

    ReservationColor(String colorCode, String colorName) {
        this.colorCode = colorCode;
        this.colorName = colorName;
    }

    public static List<ReservationColorResDto.Color> getColorCodes() {
        return Arrays.stream(ReservationColor.values())
                .map(colorEnum -> new ReservationColorResDto.Color(
                        colorEnum.getColorName(),
                        colorEnum.getColorCode()
                ))
                .collect(Collectors.toList());
    }

    public static ReservationColor fromColorCode(String colorCode) {
        return Arrays.stream(ReservationColor.values())
                .filter(color -> color.getColorCode().equals(colorCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown color code: " + colorCode));
    }
}
