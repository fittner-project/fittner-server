package kr.co.fittnerserver.entity.admin;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.admin.enums.CenterType;
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
public class Center extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "센터 키값")
    private String centerId;
    @Comment(value = "센터명")
    @Column(length = 100)
    private String centerName;
    @Comment(value = "사업자번호")
    @Column(length = 20)
    private String businessNo;
    @Comment(value = "센터주소")
    @Column(length = 100)
    private String centerAddress;
    @Comment(value = "업종")
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private CenterType centerType;
    @Comment(value = "센터 대표자명")
    @Column(length = 30)
    private String centerOwnerName;
    @Comment(value = "센터 번호")
    @Column(length = 30)
    private String centerTel;
    @Comment(value = "센터 삭제유무")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String centerDeleteYn;
    @ManyToOne
    @JoinColumn(name = "center_group_id")
    private CenterGroup centerGroup;

    @OneToOne
    @JoinColumn(name = "file_group_id")
    private FileGroup fileGroup;
}
