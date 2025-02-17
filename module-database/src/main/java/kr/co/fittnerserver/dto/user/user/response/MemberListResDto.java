package kr.co.fittnerserver.dto.user.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.fittnerserver.entity.user.enums.MemberGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberListResDto {
    @Schema(description = "회원 ID", example = "1")
    private String memberId;
    @Schema(description = "회원 이름", example = "홍길동")
    private String memberName;
    @Schema(description = "회원 전화번호", example = "010-1234-5678")
    private String memberPhone;
    @Schema(description = "회원 나이", example = "30")
    private String memberAge;
    @Schema(description = "회원 성별", example = "M")
    private MemberGender memberGender;
    @Schema(description = "회원 총 수", example = "10")
    private long memberTotalCount;
}
