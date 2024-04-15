package com.finale.lesson.dto;

import com.finale.entity.Lesson;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LessonDetailBasicDTO extends LessonBasicDTO {

    // 4회 수업에 대한 내용
    @Schema(description = "첫 번째 수업 날짜", example = "2024-01-08")
    protected String firstDate;
    @Schema(description = "첫 번째 수업 시작 시간", example = "07:10")
    protected String startTime;
    @Schema(description = "첫 번째 수업 종료 시간", example = "08:30")
    protected String endTime;

    @Schema(description = "두 번째 수업 날짜", example = "2024-01-15")
    protected String secondDate;
    @Schema(description = "두 번째 수업 시작 시간", example = "07:10")
    protected String secondStartTime;
    @Schema(description = "두 번째 수업 종료 시간", example = "08:30")
    protected String secondEndTime;

    @Schema(description = "세 번째 수업 날짜", example = "2024-01-22")
    protected String thirdDate;
    @Schema(description = "세 번째 수업 시작 시간", example = "07:10")
    protected String thirdStartTime;
    @Schema(description = "세 번째 수업 종료 시간", example = "08:30")
    protected String thirdEndTime;

    @Schema(description = "네 번째 수업 날짜", example = "2024-01-29")
    protected String fourthDate;
    @Schema(description = "네 번째 수업 시작 시간", example = "07:40")
    protected String fourthStartTime;
    @Schema(description = "네 번째 수업 종료 시간", example = "09:00")
    protected String fourthEndTime;

    @Schema(description = "수업 횟수 및 비용", example = "4회 18만원")
    protected String cost;
    @Schema(description = "(코치 1명 당) 수업 정원", example = "1:8 수업")
    protected String classSize;

    public LessonDetailBasicDTO(Lesson lesson) {
        super(lesson);

        this.firstDate = lesson.getTimetable().getDate();
        this.startTime = lesson.getTimetable().getStartTime();
        this.endTime = lesson.getTimetable().getEndTime();

        this.secondDate = lesson.getTimetable().getSecondDate();
        this.secondStartTime = lesson.getTimetable().getSecondStartTime();
        this.secondEndTime = lesson.getTimetable().getSecondEndTime();

        this.thirdDate = lesson.getTimetable().getThirdDate();
        this.thirdStartTime = lesson.getTimetable().getThirdStartTime();
        this.thirdEndTime = lesson.getTimetable().getThirdEndTime();

        this.fourthDate = lesson.getTimetable().getFourthDate();
        this.fourthStartTime = lesson.getTimetable().getFourthStartTime();
        this.fourthEndTime = lesson.getTimetable().getFourthEndTime();

        this.cost = lesson.getTimetable().getCost();
        this.classSize = "1:" + lesson.getTimetable().getClassSize() + " 수업";
    }
}
