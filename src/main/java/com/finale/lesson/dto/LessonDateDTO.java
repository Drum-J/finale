package com.finale.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LessonDateDTO(@Schema(description = "수업 날짜", example = "2024-01-08") String date,
                            @Schema(description = "수업 시작 시간", example = "07:10") String startTime,
                            @Schema(description = "수업 종료 시간", example = "08:30") String endTime) {
}
