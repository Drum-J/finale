package com.finale.entity;

import jakarta.persistence.Column;
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
public class SmsTemplate extends TimeStamped {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String text;

    public SmsTemplate(String text) {
        this.text = text;
    }
}
