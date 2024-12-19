package kr.co.fittnerserver.entity.admin;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.entity.user.CenterJoin;
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
public class CenterProduct extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "센터 상품 키값")
    private String centerProductId;
    @Comment(value = "센터 상품명")
    @Column(length = 100)
    private String centerProductName;
    @Comment(value = "센터 상품 기간(개월)")
    private int centerProductPeriod;
    @Comment(value = "센터 상품 횟수")
    private int centerProductCount;
    @Comment(value = "센터 상품 가격")
    private int centerProductPrice;
    @Comment(value = "센터 상품 삭제여부")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String centerProductDeleteYn;
    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;
}
