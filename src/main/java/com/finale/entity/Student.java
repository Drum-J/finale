package com.finale.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Student extends TimeStamped {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Setter
    private String name;
    private String email;
    private String phoneNumber;

    @Builder
    public Student(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
