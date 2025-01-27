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
public class ApiLog {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(38)")
    @Comment(value = "로그 키값")
    private String apiLogId;

    @Comment(value = "로그추적ID")
    @Column(length = 38)
    private String mdcId;

    @Comment(value = "요청일자")
    @Column(length = 8)
    private String reqDate;

    @Comment(value = "요청시간")
    @Column(length = 6)
    private String reqTime;

    @Comment(value = "헤더")
    @Column(columnDefinition = "TEXT")
    private String header;

    @Comment(value = "클라이언트IP")
    @Column(length = 20)
    private String clientIp;

    @Comment(value = "호출uri")
    @Column(length = 300)
    private String callUri;

    @Comment(value = "http메서드")
    @Column(length = 10)
    private String reqMethod;

    @Comment(value = "컨텐츠타입")
    @Column(length = 10)
    private String contentType;

    @Comment(value = "요청파라미터")
    @Column(columnDefinition = "TEXT")
    private String reqParam;

    @Comment(value = "응답일자")
    @Column(length = 8)
    private String resDate;

    @Comment(value = "응답시간")
    @Column(length = 6)
    private String resTime;

    @Comment(value = "응답코드")
    @Column(length = 10)
    private String resCode;

    @Comment(value = "응답결과")
    @Column(columnDefinition = "TEXT")
    private String resResult;

}
