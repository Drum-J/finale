package com.finale.lesson.dto;

import com.querydsl.core.annotations.QueryProjection;

public record LessonWithDateAndLocationDTO(Long lessonId, String title,
                                           int days, String startTime,
                                           String endTime) {

    @QueryProjection
    public LessonWithDateAndLocationDTO {
    }
}
