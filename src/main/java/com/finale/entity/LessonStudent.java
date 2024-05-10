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

    public LessonStudent(Lesson lesson, Student student) {
        this.lesson = lesson;
        this.student = student;
    }

    public void depositConfirm() throws IllegalArgumentException {
        if (this.deposit) {
            throw new IllegalArgumentException("이미 입금 확인을 완료했습니다.");
        }
        this.deposit = true;
    }
}
