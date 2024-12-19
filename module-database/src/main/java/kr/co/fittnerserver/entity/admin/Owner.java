package kr.co.fittnerserver.entity.admin;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.admin.enums.CenterType;
import kr.co.fittnerserver.entity.admin.enums.OwnerStatus;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.common.FileGroup;
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
public class Owner extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "대표 키값")
    private String ownerId;

    @Comment(value = "대표 아이디")
    @Column(length = 20)
    private String ownerUserId;

    @Comment(value = "대표 비밀번호")
    private String ownerPassword;

    @Comment(value = "대표 삭제유무")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String ownerDeleteYn;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private OwnerStatus ownerStatus;
}
