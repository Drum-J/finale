package com.finale.location.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LocationResponseDTO {

    @Schema(description = "레슨 장소 ID", example = "1")
    private Long id;

    @Schema(description = "레슨 장소 이름", example = "계명대학교")
    private String name;

    public LocationResponseDTO(Long id,String name) {
        this.id = id;
        this.name = name;
    }
}
