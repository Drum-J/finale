package com.finale.lesson.controller;

import com.finale.common.ApiResponse;
import com.finale.lesson.service.LessonService;
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
@RequestMapping("/api/lesson")
public class LessonController {

    private final LessonService lessonService;

    @Operation(summary = "[수강생용] 레슨 디테일 조회 API", description = "수강생용 레슨 디테일 조회 입니다. 레슨 ID 값이 필요합니다.")
    @GetMapping("/{id}")
    public ApiResponse getLessonDetails(@PathVariable(name = "id") Long id) {
        return lessonService.getLessonDetails(id);
    }

    @Operation(summary = "[수강생용] 모든 레슨 조회 API", description = "수강생용 모든 레슨 조회 API 입니다.")
    @GetMapping("/list")
    public ApiResponse getAllLesson() {
        return lessonService.getAllLesson();
    }

    @Operation(summary = "[수강생용] 장소 별 레슨 조회 API")
    @GetMapping("/withLocation/{locationName}")
    public ApiResponse lessonsByLocation(@PathVariable("locationName") String name) {
        return lessonService.getLessonsByLocation(name);
    }

    @Operation(summary = "레슨 공지사항 조회 API")
    @GetMapping("/notice")
    public ApiResponse lessonNotice() {
        return lessonService.getNotice();
    }
}
