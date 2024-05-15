package com.finale.location.service;

import com.finale.entity.Location;
import com.finale.location.dto.LocationCreateDTO;
import com.finale.location.dto.LocationResponseDTO;
import com.finale.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    private final LocationRepository locationRepository;

    @Transactional
    public void createLocation(LocationCreateDTO dto) {
        Location location = dto.convertToEntity();
        locationRepository.save(location);
    }

    public List<LocationResponseDTO> getLocationList() {
        return locationRepository.findAll().stream()
                .map(LocationResponseDTO::new).toList();
    }
}
