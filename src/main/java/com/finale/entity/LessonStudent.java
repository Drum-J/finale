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
@EqualsAndHashCode(of = {"lesson", "student"}, callSuper = false)
public class LessonStudent extends TimeStamped {

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

    private boolean newbie; // 신청 당시 회원 신규/기존 상태 (true 뉴비, false 기존)

    private boolean remind; // 독촉문자 발송 여부 (true 발송, false 미발송)

    public LessonStudent(Lesson lesson, Student student) {
        this.lesson = lesson;
        this.student = student;
        this.newbie = student.isNewbie();
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

    public void notNewbie() {
        this.newbie = false;
    }

    public void sendRemind() {
        this.remind = true;
    }
}
