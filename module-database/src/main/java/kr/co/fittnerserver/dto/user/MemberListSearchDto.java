package kr.co.fittnerserver.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MemberListSearchDto {
    @Schema(description = "회원명", example = "홍길동")
    private String memberName;
    @Schema(description = "회원 휴대폰 끝번호", example = "2058")
    private String memberNo;
}
