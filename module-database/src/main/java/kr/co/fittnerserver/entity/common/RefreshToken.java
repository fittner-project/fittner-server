package kr.co.fittnerserver.entity.common;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.user.Trainer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseTimeEntity {

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

    //TODO 대표 테이블 매핑

}
