package kr.co.fittnerserver.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResDto {
    private String accessToken;
    private String refreshTokenId;
}
