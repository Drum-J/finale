package com.finale.location.dto;

import lombok.Data;

@Data
public class LocationResponseDTO {
    private String name;

    public LocationResponseDTO(String name) {
        this.name = name;
    }
}
