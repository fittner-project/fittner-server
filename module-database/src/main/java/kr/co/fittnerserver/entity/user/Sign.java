package kr.co.fittnerserver.entity.user;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.common.FileGroup;
import kr.co.fittnerserver.entity.user.enums.SignType;
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
public class Sign extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "서명 키값")
    private String signId;

    @Comment(value = "메모")
    @Column(length = 100)
    private String signMemo;

    @Lob
    @Comment(value = "서명 이미지")
    private byte[] signImage;

    @Enumerated(EnumType.STRING)
    @Comment(value = "서명타입")
    @Column(length = 10)
    private SignType signType;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "file_group_id")
    private FileGroup fileGroup;
}
