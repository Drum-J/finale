package com.finale.location.dto;

import lombok.Data;

@Data
public class LocationResponseDTO {
    private Long id;
    private String name;

    public LocationResponseDTO(Long id,String name) {
        this.id = id;
        this.name = name;
    }
}
