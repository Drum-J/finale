package com.finale.location.controller;

import com.finale.location.dto.LocationCreateDTO;
import com.finale.location.dto.LocationResponseDTO;
import com.finale.location.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/create")
    @Operation(
            method = "POST",
            summary = "레슨 장소 생성 API",
            description = "LocationController.createLocation()"
    )
    public void createLocation(@RequestBody LocationCreateDTO dto) {
        locationService.createLocation(dto);
    }

    @GetMapping("/list")
    @Operation(
            method = "GET",
            summary = "getLocationsList()",
            description = "레슨 장소 리스트 API"
    )
    public List<LocationResponseDTO> getLocationList() {
        return locationService.getLocationList();
    }
}
