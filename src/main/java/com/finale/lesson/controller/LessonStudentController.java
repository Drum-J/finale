package com.finale.lesson.controller;

import com.finale.lesson.dto.LessonBasicDTO;
import com.finale.lesson.dto.LessonDetailBasicDTO;
import com.finale.lesson.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessonStudent/")
public class LessonStudentController {

    private final LessonService lessonService;

    @GetMapping("/{id}")
    public LessonDetailBasicDTO getLessonStudentDetails(@PathVariable(name = "id") Long id) {
        return lessonService.getLessonStudentDetails(id);
    }

    @GetMapping("/list")
    public List<LessonBasicDTO> getAllLessonStudents() {
        return lessonService.getAllLessonStudents();
    }
}
