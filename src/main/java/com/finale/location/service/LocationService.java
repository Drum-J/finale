package com.finale.location.service;

import com.finale.common.ApiResponse;
import com.finale.entity.Location;
import com.finale.lesson.repository.LessonRepository;
import com.finale.location.dto.LocationCreateDTO;
import com.finale.location.dto.LocationDeleteDTO;
import com.finale.location.dto.LocationResponseDTO;
import com.finale.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    private final LocationRepository locationRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    public ApiResponse createLocation(LocationCreateDTO dto) {
        log.info("=== 장소 생성 Service 진입 ===");
        log.info("장소 생성 DTO = {}",dto);

        Location location = dto.convertToEntity();
        locationRepository.save(location);
        return ApiResponse.successResponse("레슨 장소 저장을 완료했습니다.");
    }

    public ApiResponse getLocationList() {
        return ApiResponse.successResponse(locationRepository.findAll().stream()
                .map(LocationResponseDTO::new).toList());
    }

    @Transactional
    public ApiResponse deleteLocation(LocationDeleteDTO dto) {
        Location location = locationRepository.findByName(dto.name());
        String name = location.getName();
        lessonRepository.deleteLessonByLocationName(name);

        locationRepository.delete(location);
        return ApiResponse.successResponse("레슨 장소를 삭제했습니다. 삭제 장소 = " + name);
    }
}
