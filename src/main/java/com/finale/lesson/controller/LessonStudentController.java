package com.finale.lesson.controller;

import com.finale.common.ApiResponse;
import com.finale.lesson.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessonStudent/")
public class LessonStudentController {

    private final LessonService lessonService;

    @Operation(
            method = "GET",
            summary = "[수강생] 레슨 디테일 조회 API",
            description = "getLessonStudentDetails()"
    )
    @GetMapping("/{id}")
    public ApiResponse getLessonStudentDetails(@PathVariable(name = "id") Long id) {
        return lessonService.getLessonStudentDetails(id);
    }

    @Operation(
            method = "GET",
            summary = "[수강생] 모든 레슨 조회 API",
            description = "getAllLessonStudents()"
    )
    @GetMapping("/list")
    public ApiResponse getAllLessonStudents() {
        return lessonService.getAllLessonStudents();
    }
}
