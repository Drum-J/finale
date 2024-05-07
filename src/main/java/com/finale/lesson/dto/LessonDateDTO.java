package com.finale.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LessonDateDTO {
    @Schema(description = "수업 날짜", example = "2024-01-08")
    private String date;
    @Schema(description = "수업 시작 시간", example = "07:10")
    private String startTime;
    @Schema(description = "수업 종료 시간", example = "08:30")
    private String endTime;

    public LessonDateDTO(String date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
