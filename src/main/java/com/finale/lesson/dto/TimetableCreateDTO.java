package com.finale.lesson.dto;

import com.finale.entity.Timetable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

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

    private List<LessonDateDTO> lessonDates;

    @Schema(description = "수업 횟수 및 비용", example = "4회 18만원")
    private String cost;
    @Schema(description = "(코치 1명 당) 수업 정원", example = "8")
    private int classSize;

    public Timetable convertToEntity() {
        return Timetable.builder()
                .title(this.title)
                .location(this.location)
                .days(this.days)
                .date(this.lessonDates.get(0).date())
                .startTime(this.lessonDates.get(0).startTime())
                .endTime(this.lessonDates.get(0).endTime())
                .secondDate(this.lessonDates.get(1).date())
                .secondStartTime(this.lessonDates.get(1).startTime())
                .secondEndTime(this.lessonDates.get(1).endTime())
                .thirdDate(this.lessonDates.get(2).date())
                .thirdStartTime(this.lessonDates.get(2).startTime())
                .thirdEndTime(this.lessonDates.get(2).endTime())
                .fourthDate(this.lessonDates.get(3).date())
                .fourthStartTime(this.lessonDates.get(3).startTime())
                .fourthEndTime(this.lessonDates.get(3).endTime())
                .cost(this.cost)
                .classSize(this.classSize)
                .totalClassSize(this.classSize * this.coaches)
                .build();
    }
}
