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

    @Schema(description = "코치 ID List", example = "[1,2]")
    private List<Long> coaches;

    @Schema(description = "레슨 요일", example = "월요일")
    private int days;

    private List<LessonDateDTO> lessonDates;

    @Schema(description = "수업 횟수 및 비용", example = "4회 18만원")
    private String cost;
    @Schema(description = "(코치 1명 당) 수업 정원", example = "8")
    private int classSize;

    public Timetable convertToEntity() {
        Timetable timetable = Timetable.builder()
                .title(this.title)
                .location(this.location)
                .days(this.days)
                .date(this.lessonDates.get(0).date())
                .startTime(this.lessonDates.get(0).startTime())
                .endTime(this.lessonDates.get(0).endTime())
                .cost(this.cost)
                .classSize(this.classSize)
                .totalClassSize(this.classSize * this.coaches.size())
                .build();

        if (this.lessonDates.size() > 1) {
            timetable.setSecondDate(this.lessonDates.get(1).date());
            timetable.setSecondStartTime(this.lessonDates.get(1).startTime());
            timetable.setSecondEndTime(this.lessonDates.get(1).endTime());
        }

        if (this.lessonDates.size() > 2) {
            timetable.setThirdDate(this.lessonDates.get(2).date());
            timetable.setThirdStartTime(this.lessonDates.get(2).startTime());
            timetable.setThirdEndTime(this.lessonDates.get(2).endTime());
        }

        if (this.lessonDates.size() > 3) {
            timetable.setFourthDate(this.lessonDates.get(3).date());
            timetable.setFourthStartTime(this.lessonDates.get(3).startTime());
            timetable.setFourthEndTime(this.lessonDates.get(3).endTime());
        }

        return timetable;
    }
}
