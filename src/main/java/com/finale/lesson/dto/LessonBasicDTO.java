package com.finale.lesson.dto;

import com.finale.entity.Lesson;
import lombok.Getter;

import java.util.List;

@Getter
public class LessonBasicDTO {
    protected Long id; // Lesson id
    protected String title;
    protected String location; // 장소
    protected String day; // 요일
    protected List<String> coaches; // 강사

    protected int enrolment; // 총 수강 신청 가능 인원
    protected int currentEnrolment; // 현재 수강 신청 가능 인원

    public LessonBasicDTO(Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.location = lesson.getTimetable().getLocation();
        this.day = lesson.getTimetable().getDays();
        this.coaches = lesson.getCoaches().stream().map(c->c.getCoach().getName()).toList();

        this.enrolment = lesson.getEnrolment();
        this.currentEnrolment = lesson.getCurrentEnrolment();
    }
}
