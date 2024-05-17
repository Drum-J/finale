package com.finale.coach.controller;

import com.finale.coach.dto.CoachCreateDTO;
import com.finale.coach.dto.CoachResponseDTO;
import com.finale.coach.service.CoachService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Operation(summary = "코치 리스트 API", description = "getCoachList()")
    public List<CoachResponseDTO> getCoachList() {
        return coachService.getCoachList();
    }

    @Operation(summary = "코치 생성 API", description = "createStudent()")
    @PostMapping("/create")
    public String createCoach(@RequestBody CoachCreateDTO dto) {
        return coachService.create(dto);
    }

    @Operation(summary = "코치 권한 변경 API", description = "코치 권한이 [MASTER]인 경우만 가능합니다.")
    @PostMapping("/updateRole/{id}")
    public void updateRole(@PathVariable("id") Long id) {
        coachService.updateRole(id);
    }
}
