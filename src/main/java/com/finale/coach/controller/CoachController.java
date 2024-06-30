package com.finale.coach.controller;

import com.finale.coach.service.CoachService;
import com.finale.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coach")
public class CoachController {

    private final CoachService coachService;

    @GetMapping("/list")
    @Operation(summary = "코치 리스트 API", description = "getCoachList()")
    public ApiResponse getCoachList() {
        return coachService.getCoachList();
    }

    @Operation(summary = "코치 권한 변경 API", description = "코치 권한이 [MASTER]인 경우만 가능합니다.")
    @PostMapping("/updateRole/{id}")
    public ApiResponse updateRole(@PathVariable("id") Long id) {
        return coachService.updateRole(id);
    }
}
