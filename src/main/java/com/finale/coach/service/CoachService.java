package com.finale.coach.service;

import com.finale.coach.dto.CoachResponseDTO;
import com.finale.coach.repository.CoachRepository;
import com.finale.common.ApiResponse;
import com.finale.entity.Coach;
import com.finale.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoachService {

    private final CoachRepository coachRepository;

    public ApiResponse getCoachList() {
        return ApiResponse.successResponse(coachRepository.findAll().stream()
                .map(CoachResponseDTO::new).toList());
    }

    @Transactional
    public ApiResponse updateRole(Long id) {
        Coach find = coachRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 코치를 찾을 수 없습니다."));

        find.updateRole();
        return ApiResponse.successResponse("코치 권한이 변경되었습니다.");
    }
}
