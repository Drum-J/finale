package com.finale.coach.controller;

import com.finale.coach.service.CoachService;
import com.finale.common.ApiResponse;
import com.finale.lesson.dto.TimetableCreateDTO;
import com.finale.lesson.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coach")
public class CoachController {

    private final CoachService coachService;
    private final LessonService lessonService;

    @GetMapping("/list")
    @Operation(summary = "코치 리스트 API", description = "getCoachList()")
    public ApiResponse getCoachList() {
        return coachService.getCoachList();
    }

    @Operation(summary = "[MASTER] 코치 권한 변경 API", description = "코치 권한이 [MASTER]인 경우만 가능합니다.")
    @PostMapping("/updateRole/{id}")
    public ApiResponse updateRole(@PathVariable("id") Long id) {
        return coachService.updateRole(id);
    }

    @Operation(summary = "레슨 생성 API", description = "createTimetable()")
    @PostMapping("/createLesson")
    public ApiResponse createTimetable(@RequestBody TimetableCreateDTO dto) {
        return coachService.createTimetable(dto);
    }

    @Operation(summary = "[MASTER] 수강생 입금 확인 API" ,description = "코치 권한이 [MASTER]인 경우만 가능합니다.")
    @PostMapping("/depositConfirm/{id}")
    public ApiResponse depositConfirm(@PathVariable("id") Long id) {
        return coachService.updateDeposit(id);
    }

    @Operation(summary = "[코치용] 모든 레슨 조회 API", description = "코치용 모든 레슨 조회 API입니다.")
    @GetMapping("/lesson/list")
    public ApiResponse getAllLesson() {
        return coachService.getAllLessonForCoach();
    }

    @Operation(summary = "[코치용] 장소 별 레슨 조회 API")
    @GetMapping("/lesson/withLocation/{locationName}")
    public ApiResponse lessonsByLocation(@PathVariable("locationName") String name) {
        return coachService.getLessonsByLocationForCoach(name);
    }

    @Operation(summary = "[코치용] 레슨 디테일 조회 API", description = "코치용 레슨 디테일 조회 입니다. 레슨 ID 값이 필요합니다.")
    @GetMapping("/lesson/{id}")
    public ApiResponse getLessonDetails(@PathVariable(name = "id") Long id) {
        return coachService.getLessonDetailsForCoach(id);
    }
}
