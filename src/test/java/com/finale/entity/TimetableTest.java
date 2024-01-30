package com.finale.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class TimetableTest {

    @Test
    @DisplayName("코치 2명, 수강생 2명, 총 수강 가능 인원 16명 수강 신청 성공 케이스 & 증복 신청 테스트")
    void successfulEnrollmentTest() throws Exception {

        //given
        Student student1 = createStudent(1);
        Student student2 = createStudent(2);

        Coach coach1 = createCoach(1);
        Coach coach2 = createCoach(2);

        Lesson lesson = createLesson(8);

        LessonCoach lessonCoach1 = new LessonCoach(lesson, coach1);
        LessonCoach lessonCoach2 = new LessonCoach(lesson, coach2);

        LessonStudent lessonStudent1 = new LessonStudent(lesson, student1);
        LessonStudent lessonStudent2 = new LessonStudent(lesson, student2);
        //중복 수강 신청용 새로운 객체 생성
        LessonStudent lessonStudent3 = new LessonStudent(lesson, student1);

        System.out.println("lessonStudent1 = " + lessonStudent1);
        System.out.println("lessonStudent2 = " + lessonStudent2);
        System.out.println("lessonStudent3 = " + lessonStudent3);

        //when
        lesson.addCoaches(lessonCoach1);
        lesson.addCoaches(lessonCoach2);

        lesson.setTotalClassSize(); // 실제 동작에서는 DTO 에서 만들기 때문에 set 사용 안해도 됨

        lesson.addStudent(lessonStudent1);
        lesson.addStudent(lessonStudent2);

        //then
        assertThat(lesson.getStudents().get(0).getStudent()).isEqualTo(student1);
        assertThat(lesson.getStudents().get(1).getStudent()).isEqualTo(student2);

        assertThat(lesson.getCoaches().get(0).getCoach()).isEqualTo(coach1);
        assertThat(lesson.getCoaches().get(1).getCoach()).isEqualTo(coach2);

        // 이미 수강 신청한 케이스 간단하게 추가
        assertThatThrownBy(()->lesson.addStudent(lessonStudent3))
                .isInstanceOf(IllegalStateException.class).hasMessage("이미 수강 신청 한 강의입니다.");
    }

    @Test
    @DisplayName("코치 1명, 수강생 2명, 총 수강 가능 인원 1명 수강 신청 실패 케이스")
    void unsuccessfulEnrollmentTest() throws Exception {

        //given
        Student student1 = createStudent(1);
        Student student2 = createStudent(2);

        Coach coach1 = createCoach(1);

        Lesson lesson = createLesson(1);

        LessonCoach lessonCoach1 = new LessonCoach(lesson, coach1);

        LessonStudent lessonStudent1 = new LessonStudent(lesson, student1);
        LessonStudent lessonStudent2 = new LessonStudent(lesson, student2);

        //when
        lesson.addCoaches(lessonCoach1);

        lesson.setTotalClassSize();

        lesson.addStudent(lessonStudent1);

        //then
        assertThatThrownBy(()->lesson.addStudent(lessonStudent2))
                .isInstanceOf(IllegalStateException.class).hasMessage("수강 인원을 초과하여 신청할 수 없습니다.");
    }

    private static Lesson createLesson(int classSize) {
        Timetable timetable = Timetable.builder()
                .title("2024년 2월 월요일 고려대학교")
                .location("고려대학교")
                .classSize(classSize)
                .cost("4회 15만원")
                .days("월요일")
                .date("2024-02-05")
                .startTime("07:30")
                .endTime("08:10")
                .build();

        return new Lesson(timetable);
    }

    private static Student createStudent(int i) {
        return Student.builder()
                .name("수강생" + i)
                .email("testStudent" + i + "@email.com")
                .phoneNumber("010-1122-3344")
                .build();
    }

    private static Coach createCoach(int i) {
        return Coach.builder()
                .name("코치" + i)
                .email("testCoach"+i+"@email.com")
                .coachRole(CoachRole.MASTER)
                .build();
    }
}
