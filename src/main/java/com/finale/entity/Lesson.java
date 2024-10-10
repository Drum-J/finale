package com.finale.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Lesson extends TimeStamped{

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "lesson_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;

    @OneToMany(mappedBy = "lesson",cascade = ALL, orphanRemoval = true)
    private final List<LessonCoach> coaches = new ArrayList<>();

    @OneToMany(mappedBy = "lesson",cascade = ALL)
    private final List<LessonStudent> students = new ArrayList<>();

    private int currentEnrolment; // 현재 수강 신청 인원

    private int enrolment; // 총 수강 신청 가능 인원

    private String lessonDate; // 복사용 lesson Date

    private boolean delYn; // 삭제 여부

    public Lesson(Timetable timetable) {
        this.timetable = timetable;
        this.enrolment = timetable.getTotalClassSize(); //dto 사용시 자동 설정 됨
        this.title = timetable.getTitle();
        this.lessonDate = timetable.getDate().substring(0,7);
    }

    public void setTotalClassSize() {
        this.enrolment = timetable.getClassSize() * coaches.size(); // test 에서는 set 사용해 주고 있음
    }

    //연관관계 메서드
    public void addCoaches(LessonCoach coach) {
        this.coaches.add(coach);
        coach.setLesson(this);
    }

    // 연관관계 메서드 및 비즈니스 로직
    public void addStudent(LessonStudent student) {
        if (this.students.contains(student)) {
            throw new IllegalStateException("이미 수강 신청 한 강의입니다.");
        }

        if (this.currentEnrolment < this.enrolment) {
            this.students.add(student);
            student.setLesson(this);
            this.currentEnrolment += 1;
        } else {
            throw new IllegalStateException("수강 인원을 초과하여 신청할 수 없습니다.");
        }
    }

    public void enrolmentMinus() {
        this.currentEnrolment--;
    }

    public void update(Timetable timetable) {
        this.timetable = timetable;
        this.title = timetable.getTitle();
        this.enrolment = timetable.getTotalClassSize();
        this.lessonDate = timetable.getDate().substring(0,7);
    }

    public void delete() {
        this.delYn = true;
    }
}
