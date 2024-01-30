package com.finale.lesson.dto;

import com.finale.entity.Lesson;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class LessonResponseDTO {
    private Long id; // Lesson Id 값을 사용해야함.
    private String location; // 장소
    private String day; // 요일
    private List<String> coaches; // 강사
    private List<String> students; // 학생

    private int enrolment; // 총 수강 신청 가능 인원
    private int currentEnrolment; // 현재 수강 신청 가능 인원

    public static LessonResponseDTO createDTO(Lesson lesson) {
        LessonResponseDTO dto = new LessonResponseDTO();

        dto.id = lesson.getId();
        dto.location = lesson.getTimetable().getLocation();
        dto.day = lesson.getTimetable().getDays();

        dto.coaches = lesson.getCoaches().stream().map(c->c.getCoach().getName()).toList();
        dto.students = lesson.getStudents().stream().map(s->s.getStudent().getName()).toList();

        dto.enrolment = lesson.getEnrolment();
        dto.currentEnrolment = lesson.getCurrentEnrolment();

        return dto;
    }
}
