package com.finale.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Coach extends TimeStamped {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "coach_id")
    private Long id;

    private String email;

    private String name;

    private String phoneNumber;

    @Enumerated(value = STRING)
    private CoachRole coachRole;

    @Builder
    public Coach(String email,String name,String phoneNumber, CoachRole coachRole) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.coachRole = coachRole;
    }

    public void updateRole() {
        this.coachRole = CoachRole.MASTER;
    }
}
