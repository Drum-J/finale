package com.finale.lesson.dto;

import com.finale.entity.LessonStudent;

public record LessonStudentDetailDTO(
        Long id,
        Long studentId,
        String name,
        String phoneNumber,
        boolean deposit
) {
    public LessonStudentDetailDTO(LessonStudent lessonStudent) {
        this(lessonStudent.getId(),
                lessonStudent.getStudent().getId(),
                lessonStudent.getStudent().getName(),
                lessonStudent.getStudent().getPhoneNumber(),
                lessonStudent.isDeposit());
    }
}
