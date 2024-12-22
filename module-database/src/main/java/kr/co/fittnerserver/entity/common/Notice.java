package kr.co.fittnerserver.entity.common;

import jakarta.persistence.*;
import kr.co.fittnerserver.entity.admin.Center;
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
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "공지사항 키값")
    private String noticeId;

    @Comment(value = "공지사항 제목")
    @Column(length = 100)
    private String noticeTitle;

    @Comment(value = "공지사항 내용")
    @Column(columnDefinition = "TEXT")
    private String noticeContent;

    @Comment(value = "공통공지 여부")
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String noticeEveryCenterYn;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

}
