package com.finale.lesson.controller;

import com.finale.lesson.dto.TimetableCreateDTO;
import com.finale.lesson.dto.LessonResponseDTO;
import com.finale.lesson.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/timetable")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/create")
    public void createTimetable(@RequestBody TimetableCreateDTO dto) {
        lessonService.createTimetable(dto);
    }

    @GetMapping("/{id}")
    public LessonResponseDTO getTimetableDetails(@PathVariable(name = "id") Long id) {
        return lessonService.getTimetableDetails(id);
    }

    @GetMapping("/list")
    public List<LessonResponseDTO> getAllLesson() {
        return lessonService.getAllLesson();
    }
}
