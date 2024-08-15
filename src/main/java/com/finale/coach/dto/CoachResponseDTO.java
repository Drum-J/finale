package com.finale.coach.dto;

import com.finale.entity.Coach;
import com.finale.entity.CoachRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CoachResponseDTO {

    @Schema(description = "코치 ID", example = "1")
    private final Long id;

    @Schema(description = "코치 이름", example = "황승호")
    private final String name;

    @Schema(description = "코치 전화번호", example = "010-1234-5678")
    private final String phoneNumber;

    @Schema(description = "코치 역할", example = "MASTER")
    private final CoachRole coachRole;

    @Schema(description = "코치 이미지 URL")
    private final String url;

    @Schema(description = "코치 이력")
    private final String resume;

    public CoachResponseDTO(Coach coach) {
        this.id = coach.getId();
        this.name = coach.getName();
        this.phoneNumber = coach.getPhoneNumber();
        this.coachRole = coach.getCoachRole();
        this.url = coach.getProfile();
        this.resume = coach.getResume();
    }
}
