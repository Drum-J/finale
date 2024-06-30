package com.finale.date;

import com.finale.entity.Coach;
import com.finale.entity.Lesson;
import com.finale.entity.LessonCoach;
import com.finale.entity.LessonStudent;
import com.finale.entity.Student;
import com.finale.entity.Timetable;
import com.finale.lesson.dto.LessonDateDTO;
import com.finale.lesson.repository.LessonCoachRepository;
import com.finale.lesson.repository.LessonRepository;
import com.finale.lesson.repository.LessonStudentRepository;
import com.finale.lesson.repository.TimetableRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class DateSpringTest {

    @Autowired LessonRepository lessonRepository;
    @Autowired TimetableRepository timetableRepository;
    @Autowired LessonCoachRepository lessonCoachRepository;
    @Autowired LessonStudentRepository lessonStudentRepository;

    @Test
    void dateCopy() throws Exception {
        //given
        Lesson lesson = lessonRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("해당 레슨을 찾을 수 없습니다."));

        Timetable timetable = lesson.getTimetable();
        List<Coach> coaches = lesson.getCoaches().stream().map(LessonCoach::getCoach).toList();
        List<Student> students = lesson.getStudents().stream().map(LessonStudent::getStudent).toList();

        //when
        List<LessonDateDTO> lessonDate = dateCopy(timetable);
        Timetable copyTimetable = new Timetable(timetable, lessonDate);
        timetableRepository.save(copyTimetable);
        log.info("새로운 Timetable = {}",copyTimetable.getId());

        //then
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

    private static List<LessonDateDTO> dateCopy(Timetable timetable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate originalDate = LocalDate.parse(timetable.getDate(), formatter); // 해당 레슨의 첫번째 날짜
        DayOfWeek dayOfWeek = originalDate.getDayOfWeek(); //날짜의 요일 구하기
        LocalDate nextMonthFirstDay = originalDate.plusMonths(1).withDayOfMonth(1); // 날짜 기준 다음 달의 첫번째 날 구하기

        List<LessonDateDTO> dates = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            LocalDate localDate = nextMonthFirstDay.with(TemporalAdjusters.nextOrSame(dayOfWeek)).plusWeeks(i); // 다음 달의 첫번째 날짜를 기준으로 첫번째 요일(dayOfWeek) 구하기
            log.info("바뀐 날짜 = {}",localDate);
            dates.add(new LessonDateDTO(localDate.toString(), null, null));
        }

        return dates;
    }
}
