package kr.co.fittnerserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("blackListToken")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlackListToken {
    @Id
    private String id;
    @Indexed
    private String accessToken;
    @Indexed
    private String trainerId;

    public BlackListToken(String accessToken, String trainerId) {
        this.accessToken = accessToken;
        this.trainerId = trainerId;
    }
}
