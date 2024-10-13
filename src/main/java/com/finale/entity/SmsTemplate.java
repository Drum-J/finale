package com.finale.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(indexes = @Index(name = "idx_sms_type", columnList = "smsType"))
public class SmsTemplate extends TimeStamped {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String text;

    @Enumerated(value = STRING)
    private SmsType smsType;

    public SmsTemplate(String text, SmsType type) {
        this.text = text;
        this.smsType = type;
    }

    public void update(String text) {
        this.text = text;
    }
}
