package com.finale.scheduler;

import com.finale.entity.EnrollmentStatus;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class StatusDTO {
    private final boolean enrollment;
    private final boolean restLesson;

    public StatusDTO(EnrollmentStatus status) {
        this.enrollment = status.isEnrollmentStatus();
        this.restLesson = status.isRestLessonStatus();
    }
}
