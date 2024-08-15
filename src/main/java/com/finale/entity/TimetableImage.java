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
public class TimetableImage extends TimeStamped{

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String url;

    @Column(name = "s3_key")
    private String s3Key;

    public TimetableImage(String url, String s3Key) {
        this.url = url;
        this.s3Key = s3Key;
    }

    public void updateImage(String url, String s3Key) {
        this.url = url;
        this.s3Key = s3Key;
    }
}
