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
public class Notice extends TimeStamped{

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column(length = 1000)
    private String contents;

    public Notice(String contents) {
        this.contents = contents;
    }
}
