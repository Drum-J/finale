package com.finale.coach.service;

import com.finale.coach.dto.CopyRequestDTO;
import com.finale.common.ApiResponse;
import com.finale.entity.Coach;
import com.finale.entity.Lesson;
import com.finale.entity.LessonCoach;
import com.finale.entity.LessonStudent;
import com.finale.entity.Student;
import com.finale.entity.Timetable;
import com.finale.exception.ResourceNotFoundException;
import com.finale.lesson.dto.LessonDateDTO;
import com.finale.lesson.repository.LessonCoachRepository;
import com.finale.lesson.repository.LessonRepository;
import com.finale.lesson.repository.LessonStudentRepository;
import com.finale.lesson.repository.TimetableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CopyService {

    private final LessonRepository lessonRepository;
    private final TimetableRepository timetableRepository;
    private final LessonCoachRepository lessonCoachRepository;
    private final LessonStudentRepository lessonStudentRepository;

    public ApiResponse lessonCopy(CopyRequestDTO dto) {
        log.info("=== lessonCopy 메서드 진입 ===");
        List<Lesson> byLessonDate = lessonRepository.findByLessonDate(dto.lessonDate());

        if (byLessonDate.isEmpty()) {
            throw new ResourceNotFoundException("해당 년월에는 레슨이 없습니다.");
        }

        for (Lesson lesson : byLessonDate) {
            makeCopyLesson(lesson);
        }

        return ApiResponse.successResponse(dto.lessonDate() + " 레슨 복사를 완료했습니다");
    }

    private void makeCopyLesson(Lesson lesson) {
        Timetable timetable = lesson.getTimetable();
        List<Coach> coaches = lesson.getCoaches().stream().map(LessonCoach::getCoach).toList();
        List<Student> students = lesson.getStudents().stream()
                .filter(lessonStudent -> !lessonStudent.isRestLesson())
                .map(LessonStudent::getStudent).toList();

        List<LessonDateDTO> lessonDate = dateCopy(timetable);
        Timetable copyTimetable = new Timetable(timetable, lessonDate);
        timetableRepository.save(copyTimetable);
        log.info("새로운 Timetable = {}",copyTimetable.getId());

        Lesson copyLesson = new Lesson(copyTimetable);
        lessonRepository.save(copyLesson);
        log.info("새로운 Lesson = {}",copyLesson.getId());

        coaches.forEach(coach -> {
            LessonCoach lessonCoach = new LessonCoach(copyLesson, coach);
            lessonCoachRepository.save(lessonCoach);
            log.info("새로운 LessonCoach = {} / {}", lessonCoach.getId(), lessonCoach.getCoach().getId());
            copyLesson.addCoaches(lessonCoach);
        });

        students.forEach(student -> {
            LessonStudent lessonStudent = new LessonStudent(copyLesson, student);
            lessonStudentRepository.save(lessonStudent);
            log.info("새로운 LessonStudent = {} / {}", lessonStudent.getId(), lessonStudent.getStudent().getId());
            copyLesson.addStudent(lessonStudent);
        });
    }

    private List<LessonDateDTO> dateCopy(Timetable timetable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate originalDate = LocalDate.parse(timetable.getDate(), formatter); // 해당 레슨의 첫번째 날짜
        DayOfWeek dayOfWeek = originalDate.getDayOfWeek(); //날짜의 요일 구하기
        LocalDate nextMonthFirstDay = originalDate.plusMonths(1).withDayOfMonth(1); // 날짜 기준 다음 달의 첫번째 날 구하기

        List<LessonDateDTO> dates = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            LocalDate localDate = nextMonthFirstDay.with(TemporalAdjusters.nextOrSame(dayOfWeek)).plusWeeks(i); // 다음 달의 첫번째 날짜를 기준으로 첫번째 요일(dayOfWeek) 구하기
            dates.add(new LessonDateDTO(localDate.toString(), null, null));
        }

        return dates;
    }
}
