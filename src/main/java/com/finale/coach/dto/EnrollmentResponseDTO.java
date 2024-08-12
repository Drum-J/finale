package com.finale.coach.dto;

import com.querydsl.core.annotations.QueryProjection;

public record EnrollmentResponseDTO(Long lessonId, String location,
                                    String lessonDate, int lessonDays,
                                    Long studentId, String studentName, String phoneNumber,
                                    boolean deposit , boolean restLesson) {

    @QueryProjection
    public EnrollmentResponseDTO {
    }
}
