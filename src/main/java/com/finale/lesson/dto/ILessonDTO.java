package com.finale.lesson.dto;

import com.finale.entity.Lesson;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ILessonDTO {
    private final Long id;
    private final String locationName;
    private final String cost;
    private final int day;
    private final int maxStudents;
    private final int currentEnrollment;
    private final int studentsPerCoach;
    private final List<LessonDateDTO> lessonDates = new ArrayList<>();

    public ILessonDTO(Lesson lesson) {
        this.id = lesson.getId();
        this.locationName = lesson.getTimetable().getLocation();
        this.cost = lesson.getTimetable().getCost();
        this.day = lesson.getTimetable().getDays();
        this.maxStudents = lesson.getEnrolment();
        this.currentEnrollment = lesson.getCurrentEnrolment();
        this.studentsPerCoach = lesson.getTimetable().getClassSize();

        lessonDates.add(new LessonDateDTO(lesson.getTimetable().getDate(), lesson.getTimetable().getStartTime(), lesson.getTimetable().getEndTime()));
        lessonDates.add(new LessonDateDTO(lesson.getTimetable().getSecondDate(), lesson.getTimetable().getSecondStartTime(), lesson.getTimetable().getSecondEndTime()));
        lessonDates.add(new LessonDateDTO(lesson.getTimetable().getThirdDate(), lesson.getTimetable().getThirdStartTime(), lesson.getTimetable().getThirdEndTime()));
        lessonDates.add(new LessonDateDTO(lesson.getTimetable().getFourthDate(), lesson.getTimetable().getFourthStartTime(), lesson.getTimetable().getFourthEndTime()));
    }
}
