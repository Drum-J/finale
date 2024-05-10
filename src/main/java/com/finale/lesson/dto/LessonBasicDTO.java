package com.finale.lesson.dto;

import com.finale.entity.Lesson;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class LessonBasicDTO {

    @Schema(description = "레슨 ID", example = "1")
    protected Long id; // Lesson id

    @Schema(description = "레슨명", example = "2024년 1월 월요일 계명대학교")
    protected String title;

    @Schema(description = "레슨 장소", example = "계명대학교")
    protected String location; // 장소

    @Schema(description = "레슨 요일", example = "월요일")
    protected String day; // 요일

    /*
    @Schema(description = "레슨 코치 이름 List", example = "[황승호, 우주형]")
    protected List<String> coaches; // 강사
    */

    @Schema(description = "총 수강 신청 가능 인원", example = "16")
    protected int enrolment; // 총 수강 신청 가능 인원

    @Schema(description = "현재 수강 신청 가능 인원", example = "14")
    protected int currentEnrolment; // 현재 수강 신청 가능 인원

    public LessonBasicDTO(Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.location = lesson.getTimetable().getLocation();
        this.day = lesson.getTimetable().getDays();
        // this.coaches = lesson.getCoaches().stream().map(c->c.getCoach().getName()).toList();

        this.enrolment = lesson.getEnrolment();
        this.currentEnrolment = lesson.getCurrentEnrolment();
    }
}
