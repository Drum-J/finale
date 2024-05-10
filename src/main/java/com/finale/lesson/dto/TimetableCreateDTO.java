package com.finale.lesson.dto;

import com.finale.entity.Timetable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TimetableCreateDTO {
    @Schema(description = "수업 제목", example = "2024년 1월 월요일 계명대학교")
    private String title;

    @Schema(description = "장소", example = "계명대학교")
    private String location;

    @Schema(description = "코치 수", example = "3")
    private int coaches;

    @Schema(description = "레슨 요일", example = "월요일")
    private String days;

    @Schema(description = "레슨 첫 번째 수업", exampleClasses = LessonDateDTO.class)
    private LessonDateDTO first;

    @Schema(description = "레슨 두 번째 수업", exampleClasses = LessonDateDTO.class)
    private LessonDateDTO second;

    @Schema(description = "레슨 세 번째 수업", exampleClasses = LessonDateDTO.class)
    private LessonDateDTO third;

    @Schema(description = "레슨 네 번째 수업", exampleClasses = LessonDateDTO.class)
    private LessonDateDTO fourth;

    @Schema(description = "수업 횟수 및 비용", example = "4회 18만원")
    private String cost;
    @Schema(description = "(코치 1명 당) 수업 정원", example = "8")
    private int classSize;

    public Timetable convertToEntity() {
        return Timetable.builder()
                .title(this.title)
                .location(this.location)
                .days(this.days)
                .date(this.first.date())
                .startTime(this.first.startTime())
                .endTime(this.first.endTime())
                .secondDate(this.second.date())
                .secondStartTime(this.second.startTime())
                .secondEndTime(this.second.endTime())
                .thirdDate(this.third.date())
                .thirdStartTime(this.third.startTime())
                .thirdEndTime(this.third.endTime())
                .fourthDate(this.fourth.date())
                .fourthStartTime(this.fourth.startTime())
                .fourthEndTime(this.fourth.endTime())
                .cost(this.cost)
                .classSize(this.classSize)
                .totalClassSize(this.classSize * this.coaches)
                .build();
    }
}
