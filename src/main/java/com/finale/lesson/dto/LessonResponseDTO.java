package com.finale.lesson.dto;

import com.finale.entity.Lesson;
import lombok.Getter;

import java.util.List;

@Getter
public class LessonResponseDTO extends LessonBasicDTO {

    private final List<String> students; // 학생

    public LessonResponseDTO(Lesson lesson) {
        super(lesson);
        this.students = lesson.getStudents().stream().map(s->s.getStudent().getName()).toList();
    }
}
