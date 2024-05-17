package com.finale.location.controller;

import com.finale.common.ApiResponse;
import com.finale.location.dto.LocationCreateDTO;
import com.finale.location.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/create")
    @Operation(summary = "레슨 장소 생성 API", description = "createLocation()")
    public ApiResponse createLocation(@RequestBody LocationCreateDTO dto) {
        return locationService.createLocation(dto);
    }

    @GetMapping("/list")
    @Operation(summary = "레슨 장소 리스트 API", description = "getLocationsList()")
    public ApiResponse getLocationList() {
        return locationService.getLocationList();
    }
}
