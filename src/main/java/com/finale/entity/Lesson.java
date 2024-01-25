package com.finale.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
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
    @Column(name = "lesson_history_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;

    @OneToMany(mappedBy = "lesson",cascade = ALL)
    private List<LessonCoach> coaches = new ArrayList<>();

    @OneToMany(mappedBy = "lesson",cascade = ALL)
    private List<LessonStudent> students = new ArrayList<>();

    private int totalClassSize;
    public Lesson(Timetable timetable) {
        this.timetable = timetable;
        this.totalClassSize = timetable.getTotalClassSize(); //dto 사용시 자동 설정 됨
    }

    public void setTotalClassSize() {
        this.totalClassSize = timetable.getClassSize() * coaches.size(); // test 에서는 set 사용해 주고 있음
    }

    //연관관계 메서드
    public void addCoaches(LessonCoach coach) {
        this.coaches.add(coach);
        coach.setLesson(this);
    }

    // 연관관계 메서드 및 비즈니스 로직
    public void addStudent(LessonStudent student) {
        if (this.totalClassSize > 0) {
            if (this.students.contains(student)) {
                throw new IllegalStateException("이미 수강 신청 한 강의입니다.");
            }
            this.students.add(student);
            student.setLesson(this);
            this.totalClassSize -= 1;
        } else {
            throw new IllegalStateException("수강 인원을 초과하여 신청할 수 없습니다.");
        }


    }
}
