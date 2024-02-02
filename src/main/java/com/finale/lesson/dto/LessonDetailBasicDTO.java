package com.finale.lesson.dto;

import com.finale.entity.Lesson;
import lombok.Getter;

@Getter
public class LessonDetailBasicDTO extends LessonBasicDTO {

    // 4회 수업에 대한 내용
    protected String firstDate;
    protected String startTime;
    protected String endTime;

    protected String secondDate;
    protected String secondStartTime;
    protected String secondEndTime;

    protected String thirdDate;
    protected String thirdStartTime;
    protected String thirdEndTime;

    protected String fourthDate;
    protected String fourthStartTime;
    protected String fourthEndTime;

    protected String cost;
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
