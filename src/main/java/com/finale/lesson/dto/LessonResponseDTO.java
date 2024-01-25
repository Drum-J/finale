package com.finale.lesson.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class LessonResponseDTO {
    private Long id; // Lesson Id 값을 사용해야함.
    private String location; // 장소
    private String day; // 요일
    private List<String> coaches; // 강사
    private List<String> students; // 학생
}
