package kr.co.fittnerserver.entity.admin;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.admin.enums.CenterType;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
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
public class CenterGroup extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "센터 그룹 키값")
    private String centerGroupId;

    //TODO 추후 수정할수도 있음.
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

}
