package com.finale.lesson.dto;

import com.finale.entity.Lesson;
import lombok.Getter;

import java.util.List;

@Getter
public class LessonDetailResponseDTO extends LessonDetailBasicDTO {

    private final List<String> students;

    public LessonDetailResponseDTO(Lesson lesson) {
        super(lesson);
        this.students = lesson.getStudents().stream().map(s -> s.getStudent().getName()).toList();
    }
}
