package com.finale.location.controller;

import com.finale.location.dto.LocationCreateDTO;
import com.finale.location.dto.LocationResponseDTO;
import com.finale.location.service.LocationService;
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
    public void createLocation(@RequestBody LocationCreateDTO dto) {
        locationService.createLocation(dto);
    }

    @GetMapping("/list")
    public List<LocationResponseDTO> getLocationList() {
        return locationService.getLocationList();
    }
}
