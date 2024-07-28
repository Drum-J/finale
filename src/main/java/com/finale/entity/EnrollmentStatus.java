package com.finale.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class EnrollmentStatus {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private boolean enrollmentStatus;
    private boolean restLessonStatus;

    public EnrollmentStatus(boolean enrollmentStatus, boolean restLessonStatus) {
        this.enrollmentStatus = enrollmentStatus;
        this.restLessonStatus = restLessonStatus;
    }

    public void enrollmentOpen() {
        this.enrollmentStatus = true;
    }

    public void restLessonOpen() {
        this.restLessonStatus = true;
    }

    public void enrollmentClose() {
        this.enrollmentStatus = false;
    }

    public void restLessonClose() {
        this.restLessonStatus = false;
    }
}
