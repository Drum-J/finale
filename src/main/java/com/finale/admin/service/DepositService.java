package com.finale.admin.service;

import com.finale.admin.dto.DepositRequestDTO;
import com.finale.admin.dto.DepositResponseDTO;
import com.finale.common.ApiResponse;
import com.finale.lesson.repository.LessonCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepositService {

    private final LessonCustomRepository customRepository;

    public ApiResponse getDepositTrueList(DepositRequestDTO dto, boolean isDeposit) {
        List<DepositResponseDTO> depositList = customRepository.getDepositList(dto, isDeposit);
        return ApiResponse.successResponse(depositList);
    }
}
