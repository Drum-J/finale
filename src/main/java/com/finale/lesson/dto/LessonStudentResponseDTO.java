package com.finale.lesson.dto;

import com.finale.entity.Lesson;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class LessonStudentResponseDTO {
    private Long id; // Lesson id
    private String location; // 장소
    private String day; // 요일
    private List<String> coaches; // 강사

    private int enrolment; // 총 수강 신청 가능 인원
    private int currentEnrolment; // 현재 수강 신청 가능 인원

    public static LessonStudentResponseDTO createDTO(Lesson lesson) {
        LessonStudentResponseDTO dto = new LessonStudentResponseDTO();

        dto.id = lesson.getId();
        dto.location = lesson.getTimetable().getLocation().getName();
        dto.day = lesson.getTimetable().getDays();
        dto.coaches = lesson.getCoaches().stream().map(c->c.getCoach().getName()).toList();

        dto.enrolment = lesson.getEnrolment();
        dto.currentEnrolment = lesson.getCurrentEnrolment();

        return dto;
    }
}
