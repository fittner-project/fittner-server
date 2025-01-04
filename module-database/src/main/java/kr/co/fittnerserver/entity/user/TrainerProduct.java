package kr.co.fittnerserver.entity.user;

import jakarta.persistence.*;
import kr.co.fittnerserver.dto.user.MemberRegisterReqDto;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.admin.CenterProduct;
import kr.co.fittnerserver.entity.common.BaseTimeEntity;
import kr.co.fittnerserver.repository.user.TrainerProductRepository;
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
public class TrainerProduct extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "트레이너 상품 키값")
    private String trainerProductId;
    @Comment(value = "트레이너 상품명")
    @Column(length = 100)
    private String trainerProductName;
    @Comment(value = "트레이너 상품 횟수")
    private int trainerProductCount;
    @Comment(value = "트레이너 상품 가격")
    private int trainerProductPrice;
    @Comment(value = "트레이너 상품 삭제여부")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String trainerProductDeleteYn;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public TrainerProduct(MemberRegisterReqDto memberRegisterReqDto, Trainer trainer, Member member) {
        this.trainerProductName = memberRegisterReqDto.getProductName();
        this.trainerProductCount = memberRegisterReqDto.getProductCount();
        this.trainerProductPrice = memberRegisterReqDto.getProductPrice();
        this.trainerProductDeleteYn = "N";
        this.center = trainer.getCenter();
        this.trainer = trainer;
        this.member = member;
    }
}
