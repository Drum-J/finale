package com.finale.coach.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoachResponseDTO {

    @Schema(description = "코치 ID", example = "1")
    private Long id;

    @Schema(description = "코치 이름", example = "황승호")
    private String name;
}
