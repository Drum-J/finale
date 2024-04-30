package com.finale.coach.dto;

import com.finale.entity.Coach;
import com.finale.entity.CoachRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CoachCreateDTO {

    @Schema(description = "코치 이름", example = "승호")
    private String name;

    @Schema(description = "코치 E-mail", example = "coach123@naver.com")
    private String email;

    @Schema(description = "코치 전화번호", example = "010-1234-5678")
    private String phoneNumber;

    @Schema(description = "코치 역할", example = "sub")
    private String coachRole;

    public Coach toEntity() {
        return Coach.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .coachRole(CoachRole.getEnum(coachRole))
                .build();
    }
}
