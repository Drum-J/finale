package com.finale.location.dto;

import com.finale.entity.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LocationResponseDTO {

    @Schema(description = "레슨 장소 ID", example = "2")
    private Long id;

    @Schema(description = "레슨 장소 이름", example = "고려대학교")
    private String name;

    @Schema(description = "시/도", example = "서울특별시")
    private String city;

    @Schema(description = "구", example = "성북구")
    private String district;

    @Schema(description = "상세 주소", example = "안암로 145")
    private String address;

    public LocationResponseDTO(Location location) {
        this.id = location.getId();
        this.name = location.getName();
        this.city = location.getCity();
        this.district = location.getDistrict();
        this.address = location.getAddress();
    }
}
