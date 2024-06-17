package com.finale.lesson.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lesson")
public class LessonController {

    private final LessonService lessonService;

    @Operation(summary = "레슨 생성 API", description = "createTimetable()")
    @PostMapping("/create")
    public ApiResponse createTimetable(@RequestBody TimetableCreateDTO dto) {
        return lessonService.createTimetable(dto);
    }

    @Operation(summary = "레슨 디테일 조회 API", description = "쿼리 파라미터가 없는 경우 학생용, type=coach 인 경우 코치용 조회 입니다.")
    @GetMapping("/{id}")
    public ApiResponse getLessonDetails(@PathVariable(name = "id") Long id, @RequestParam(value = "type", required = false) String type) {
        if ("coach".equals(type)) {
            return lessonService.getLessonDetails(id);
        } else {
            return lessonService.getLessonStudentDetails(id);
        }
    }

    @Operation(summary = "모든 레슨 조회 API", description = "쿼리 파라미터가 없는 경우 학생용, type=coach 인 경우 코치용 조회 입니다.")
    @GetMapping("/list")
    public ApiResponse getAllLesson(@RequestParam(value = "type",required = false) String type) {
        if ("coach".equals(type)) {
            return lessonService.getAllLesson();
        } else {
            return lessonService.getAllLessonStudents();
        }
    }

    @Operation(summary = "[코치] 입금 확인 API" ,description = "코치 권한이 [MASTER]인 경우만 가능합니다.")
    @PostMapping("/depositConfirm/{id}")
    public ApiResponse depositConfirm(@PathVariable("id") Long id) {
        return lessonService.updateDeposit(id);
    }
}
