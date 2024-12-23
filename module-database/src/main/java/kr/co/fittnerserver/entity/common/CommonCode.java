package kr.co.fittnerserver.entity.common;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonCode extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "공통코드키값")
    private Long commonCodeId;

    @Comment(value = "그룹 공통코드")
    @Column(length = 50)
    private String grpCommonCode;

    @Comment(value = "공통코드")
    @Column(length = 50)
    private String commonCode;

    @Comment(value = "공통코드명")
    @Column(length = 200)
    private String commonCodeName;

    @Comment(value = "공통코드메모")
    @Column(length = 200)
    private String commonCodeMemo;

}
