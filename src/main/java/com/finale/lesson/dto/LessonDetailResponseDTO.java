package com.finale.lesson.dto;

import com.finale.entity.Lesson;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class LessonDetailResponseDTO extends LessonDetailBasicDTO {

    @Schema(description = "레슨 수강생", example = "[학생1, 학생2]")
    private final List<String> students;

    public LessonDetailResponseDTO(Lesson lesson) {
        super(lesson);
        this.students = lesson.getStudents().stream().map(s -> s.getStudent().getName()).toList();
    }
}
