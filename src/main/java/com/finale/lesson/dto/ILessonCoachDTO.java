package com.finale.lesson.dto;

import com.finale.entity.Lesson;
import lombok.Getter;

import java.util.List;

@Getter
public class ILessonCoachDTO extends ILessonDTO{

    private final List<LessonStudentDetailDTO> students;

    public ILessonCoachDTO(Lesson lesson) {
        super(lesson);
        this.students = lesson.getStudents().stream().map(LessonStudentDetailDTO::new).toList();
    }
}
