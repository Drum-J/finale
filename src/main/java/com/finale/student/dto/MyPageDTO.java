package com.finale.student.dto;

import com.finale.entity.LessonStudent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MyPageDTO {
    private final Long lessonStudentId;
    private final String month;
    private final String location;
    private final int days;
    private final String startTime;
    private final String endTime;
    private final boolean deposit;
    private final boolean restLesson;

    public MyPageDTO(LessonStudent lessonStudent) {
        this.lessonStudentId = lessonStudent.getId();
        this.month = lessonStudent.getLesson().getTimetable().getDate().substring(0,7);
        this.location = lessonStudent.getLesson().getTimetable().getLocation();
        this.days = lessonStudent.getLesson().getTimetable().getDays();
        this.startTime = lessonStudent.getLesson().getTimetable().getStartTime();
        this.endTime = lessonStudent.getLesson().getTimetable().getEndTime();
        this.deposit = lessonStudent.isDeposit();
        this.restLesson = lessonStudent.isRestLesson();
    }
}
