package com.finale.lesson.controller;

import com.finale.lesson.dto.LessonDetailResponseDTO;
import com.finale.lesson.dto.TimetableCreateDTO;
import com.finale.lesson.dto.LessonResponseDTO;
import com.finale.lesson.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lesson")
public class LessonController {

    private final LessonService lessonService;

    @Operation(
            method = "POST",
            summary = "레슨 생성 API",
            description = "createTimetable()"
    )
    @PostMapping("/create")
    public void createTimetable(@RequestBody TimetableCreateDTO dto) {
        lessonService.createTimetable(dto);
    }

    @Operation(
            method = "GET",
            summary = "[코치] 레슨 디테일 조회 API",
            description = "getLessonDetails()"
    )
    @GetMapping("/{id}")
    public LessonDetailResponseDTO getLessonDetails(@PathVariable(name = "id") Long id) {
        return lessonService.getLessonDetails(id);
    }

    @Operation(
            method = "GET",
            summary = "[코치] 모든 레슨 조회 API",
            description = "getAllLesson()"
    )
    @GetMapping("/list")
    public List<LessonResponseDTO> getAllLesson() {
        return lessonService.getAllLesson();
    }
}
