package kr.co.fittnerserver.entity.user;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TrainerRefreshToken extends BaseTimeEntity implements Serializable {

    /**
     * 리프레시토큰 키값
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "리프레시토큰 키값")
    private String refreshTokenId;

    @Comment(value = "리프레시토큰값")
    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
}
