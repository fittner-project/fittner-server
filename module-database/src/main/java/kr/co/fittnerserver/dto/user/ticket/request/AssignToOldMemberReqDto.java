package kr.co.fittnerserver.dto.user.ticket.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssignToOldMemberReqDto {

    @Schema(description = "원이용권ID", example = "3803f0e5-d4d3-11ef-b7c9-0242ac190002")
    @NotBlank
    private String originalTicketId;

    @Schema(description = "회원ID", example = "3c7ba936-d4d3-11ef-b7c9-0242ac190002")
    @NotBlank
    private String memberId;
}
