package com.finale.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Timetable extends TimeStamped {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "timetable_id")
    private Long id;

    private String title; // 레슨 제목
    private String location; // 레슨 장소

    private String days; // 레슨 요일

    private String date; //2024-01-08
    private String startTime; //07:10
    private String endTime; //08:30

    private String secondDate; //2024-01-15
    private String secondStartTime; //07:10
    private String secondEndTime; //08:30

    private String thirdDate; //2024-01-22
    private String thirdStartTime; //07:10
    private String thirdEndTime; //08:30

    private String fourthDate; //2024-01-29
    private String fourthStartTime; //07:40
    private String fourthEndTime; //09:00

    private String cost; // 4회 18만원
    private int classSize; // 1:8 수업
    private int totalClassSize; // 코치 수 x 클래스 사이즈

    @Builder
    public Timetable(String title,String location, String days,
                     String date, String startTime, String endTime,
                     String secondDate, String secondStartTime, String secondEndTime,
                     String thirdDate, String thirdStartTime, String thirdEndTime,
                     String fourthDate, String fourthStartTime, String fourthEndTime,
                     String cost, int classSize, int totalClassSize) {
        this.title = title;
        this.location = location;
        this.days = days;

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;

        this.secondDate = secondDate;
        this.secondStartTime = secondStartTime;
        this.secondEndTime = secondEndTime;

        this.thirdDate = thirdDate;
        this.thirdStartTime = thirdStartTime;
        this.thirdEndTime = thirdEndTime;

        this.fourthDate = fourthDate;
        this.fourthStartTime = fourthStartTime;
        this.fourthEndTime = fourthEndTime;

        this.cost = cost;
        this.classSize = classSize;
        this.totalClassSize = totalClassSize;
    }
}
