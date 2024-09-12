package com.finale.lesson.dto;

import com.finale.entity.Timetable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
public class TimetableCreateDTO {

    @Schema(description = "장소", example = "고려대학교")
    private String locationName;

    /*@Schema(description = "코치 ID List", example = "[1,2]")
    private List<Long> coaches;*/

    @Schema(description = "레슨 요일", example = "월요일")
    private int days;

    private List<LessonDateDTO> lessonDates;

    @Schema(description = "수업 횟수 및 비용", example = "4회 18만원")
    private String cost;
    @Schema(description = "(코치 1명 당) 수업 정원", example = "8")
    private int studentsPerCoach;
    @Schema(description = "총 정원", example = "24")
    private int maxStudents;

    public Timetable convertToEntity() {
        String title = this.locationName;
        switch (days) {
            case 1 -> title += " (월요일)";
            case 2 -> title += " (화요일)";
            case 3 -> title += " (수요일)";
            case 4 -> title += " (목요일)";
            case 5 -> title += " (금요일)";
            case 6 -> title += " (토요일)";
            case 7 -> title += " (일요일)";
        }

        Timetable timetable = Timetable.builder()
                .title(title)
                .location(this.locationName)
                .days(this.days)
                .date(this.lessonDates.get(0).date())
                .startTime(this.lessonDates.get(0).startTime())
                .endTime(this.lessonDates.get(0).endTime())
                .cost(this.cost)
                .classSize(this.studentsPerCoach)
                .totalClassSize(this.maxStudents)
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
