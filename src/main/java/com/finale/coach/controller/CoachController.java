package com.finale.coach.controller;

import com.finale.coach.dto.CoachResponseDTO;
import com.finale.coach.service.CoachService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coach")
public class CoachController {

    private final CoachService coachService;

    @GetMapping("/list")
    @Operation(
            method = "GET",
            summary = "getCoachList()",
            description = "코치 리스트 API"
    )
    public List<CoachResponseDTO> getCoachList() {
        return coachService.getCoachList();
    }
}
