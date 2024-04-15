package com.finale.location.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LocationCreateDTO {

    @Schema(description = "레슨 장소", example = "계명대학교", required = true)
    private String name;
}
