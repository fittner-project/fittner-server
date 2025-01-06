package kr.co.fittnerserver.entity.user.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ReservationColor {
    COLOR_007AFF("007AFF"),
    COLOR_FF3B30("FF3B30");

    private final String colorCode;

    ReservationColor(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }

    public static List<String> getColorCodes() {
        return Arrays.stream(ReservationColor.values())
                .map(ReservationColor::getColorCode)
                .collect(Collectors.toList());
    }

    public static ReservationColor fromColorCode(String colorCode) {
        return Arrays.stream(ReservationColor.values())
                .filter(color -> color.getColorCode().equals(colorCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown color code: " + colorCode));
    }
}
