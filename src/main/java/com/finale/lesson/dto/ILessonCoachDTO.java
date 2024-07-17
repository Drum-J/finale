package com.finale.lesson.dto;

import com.finale.entity.Lesson;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.List;

@Getter
public class ILessonCoachDTO extends ILessonDTO{

    private final List<LessonStudentDetailDTO> students;

    @QueryProjection
    public ILessonCoachDTO(Lesson lesson) {
        super(lesson);
        this.students = lesson.getStudents().stream().filter(ls ->!ls.isRestLesson()).map(LessonStudentDetailDTO::new).toList();
    }
}
