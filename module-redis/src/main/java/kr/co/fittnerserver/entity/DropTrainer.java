package kr.co.fittnerserver.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


//TODO ttl 고려
@RedisHash(value = "dropTrainer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DropTrainer {
    @Id
    private String id;
    @Indexed
    private String accessToken;
    @Indexed
    private String trainerId;

    public DropTrainer(String accessToken, String trainerId) {
        this.accessToken = accessToken;
        this.trainerId = trainerId;
    }
}
