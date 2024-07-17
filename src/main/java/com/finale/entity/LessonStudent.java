package com.finale.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = {"lesson", "student"})
public class LessonStudent {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "lesson_id")
    @Setter
    private Lesson lesson;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    private boolean deposit;

    private boolean restLesson = false; // 휴식 유무

    public LessonStudent(Lesson lesson, Student student) {
        this.lesson = lesson;
        this.student = student;
    }

    public void depositConfirm() throws IllegalStateException {
        if (this.deposit) {
            throw new IllegalStateException("이미 입금 확인을 완료했습니다.");
        }
        this.deposit = true;
    }

    public void restLesson() throws IllegalStateException {
        if (this.restLesson) {
            throw new IllegalStateException("이미 휴식 신청을 완료했습니다.");
        }
        this.restLesson = true;
        this.lesson.enrolmentMinus();
    }
}
