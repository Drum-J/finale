package com.finale.coach.dto;

import com.finale.lesson.dto.LessonDateDTO;
import java.util.List;

public record LessonUpdateDTO(Long lessonId, String locationName, /*List<Long> coaches,*/ int days,
                              List<LessonDateDTO> lessonDates, String cost, int studentsPerCoach, int maxStudents) {

}
