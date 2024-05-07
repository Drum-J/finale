package com.finale.lesson.dto;

import com.finale.entity.Lesson;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LessonDetailBasicDTO extends LessonBasicDTO {

    // 4회 수업에 대한 내용
    protected LessonDateDTO first;
    protected LessonDateDTO second;
    protected LessonDateDTO third;
    protected LessonDateDTO fourth;

    @Schema(description = "수업 횟수 및 비용", example = "4회 18만원")
    protected String cost;
    @Schema(description = "(코치 1명 당) 수업 정원", example = "1:8 수업")
    protected String classSize;

    public LessonDetailBasicDTO(Lesson lesson) {
        super(lesson);

        this.first = new LessonDateDTO(
                lesson.getTimetable().getDate(),
                lesson.getTimetable().getStartTime(),
                lesson.getTimetable().getEndTime());

        this.second = new LessonDateDTO(
                lesson.getTimetable().getSecondDate(),
                lesson.getTimetable().getSecondStartTime(),
                lesson.getTimetable().getSecondEndTime());

        this.third = new LessonDateDTO(
                lesson.getTimetable().getThirdDate(),
                lesson.getTimetable().getThirdStartTime(),
                lesson.getTimetable().getThirdEndTime());

        this.fourth = new LessonDateDTO(
                lesson.getTimetable().getFourthDate(),
                lesson.getTimetable().getFourthStartTime(),
                lesson.getTimetable().getFourthEndTime());

        this.cost = lesson.getTimetable().getCost();
        this.classSize = "1:" + lesson.getTimetable().getClassSize() + " 수업";
    }
}
