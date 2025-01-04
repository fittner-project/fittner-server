package kr.co.fittnerserver.entity.common;

import jakarta.persistence.*;
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
public class AppVersion extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "앱버전 키값")
    private String appVersionId;

    @Comment(value = "앱 OS")
    @Column(length = 3)
    private String appOs;

    @Comment(value = "최소버전")
    @Column(length = 6)
    private String minimumVersion;

    @Comment(value = "강제업데이트 여부")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String hardUpdateYn;

    @Comment(value = "업데이트 url")
    @Column(length = 100)
    private String updateUrl;

}
