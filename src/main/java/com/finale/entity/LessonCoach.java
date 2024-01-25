package com.finale.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class LessonCoach {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "lesson_id")
    @Setter
    private Lesson lesson;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coach_id")
    private Coach coach;

    public LessonCoach(Lesson lesson, Coach coach) {
        this.lesson = lesson;
        this.coach = coach;
    }
}
