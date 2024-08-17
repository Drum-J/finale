package com.finale.admin.controller;

import com.finale.admin.dto.DepositRequestDTO;
import com.finale.admin.service.DepositService;
import com.finale.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deposit")
public class DepositController {

    private final DepositService depositService;

    @Operation(summary = "[코치용] 입금 완료 List", description = "DTO 값에 따라 월별, 장소별(레슨별), 수강생 이름별 검색이 가능 합니다.")
    @GetMapping("/trueList")
    public ApiResponse getDepositTrueList(DepositRequestDTO dto) {
        return depositService.getDepositTrueList(dto,true);
    }

    @Operation(summary = "[코치용] 미입금 List", description = "DTO 값에 따라 월별, 장소별(레슨별), 수강생 이름별 검색이 가능 합니다.")
    @GetMapping("/falseList")
    public ApiResponse getDepositFalseList(DepositRequestDTO dto) {
        return depositService.getDepositTrueList(dto,false);
    }
}
