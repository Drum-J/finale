package com.finale.entity;

import com.finale.coach.dto.LessonUpdateDTO;
import com.finale.lesson.dto.LessonDateDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.List;

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

    private int days; // 레슨 요일

    private String date; //2024-01-08
    private String startTime; //07:10
    private String endTime; //08:30

    @Setter
    private String secondDate; //2024-01-15
    @Setter
    private String secondStartTime; //07:10
    @Setter
    private String secondEndTime; //08:30

    @Setter
    private String thirdDate; //2024-01-22
    @Setter
    private String thirdStartTime; //07:10
    @Setter
    private String thirdEndTime; //08:30

    @Setter
    private String fourthDate; //2024-01-29
    @Setter
    private String fourthStartTime; //07:40
    @Setter
    private String fourthEndTime; //09:00

    private String cost; // 4회 18만원
    private int classSize; // 1:8 수업
    private int totalClassSize; // 코치 수 x 클래스 사이즈

    @Builder
    public Timetable(String title,String location, int days,
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

    public Timetable(Timetable timetable, List<LessonDateDTO> lessonDate) {
        this.title = timetable.getTitle();
        this.location = timetable.getLocation();
        this.days = timetable.getDays();

        this.date = lessonDate.get(0).date();
        this.startTime = timetable.getStartTime();
        this.endTime = timetable.getEndTime();

        this.secondDate = lessonDate.get(1).date();
        this.secondStartTime = timetable.getSecondStartTime();
        this.secondEndTime = timetable.getSecondEndTime();

        this.thirdDate = lessonDate.get(2).date();
        this.thirdStartTime = timetable.getThirdStartTime();
        this.thirdEndTime = timetable.getThirdEndTime();

        this.fourthDate = lessonDate.get(3).date();
        this.fourthStartTime = timetable.getFourthStartTime();
        this.fourthEndTime = timetable.getFourthEndTime();

        this.cost = timetable.getCost();
        this.classSize = timetable.getClassSize();
        this.totalClassSize = timetable.getTotalClassSize();
    }

    public void update(LessonUpdateDTO dto) {
        String title = dto.locationName();
        switch (dto.days()) {
            case 1 -> title += " (월요일)";
            case 2 -> title += " (화요일)";
            case 3 -> title += " (수요일)";
            case 4 -> title += " (목요일)";
            case 5 -> title += " (금요일)";
            case 6 -> title += " (토요일)";
            case 7 -> title += " (일요일)";
        }

        this.title = title;
        this.location = dto.locationName();
        this.days = dto.days();

        this.date = dto.lessonDates().get(0).date();
        this.startTime = dto.lessonDates().get(0).startTime();
        this.endTime = dto.lessonDates().get(0).endTime();

        if (dto.lessonDates().size() > 1) {
            this.secondDate = dto.lessonDates().get(1).date();
            this.secondStartTime = dto.lessonDates().get(1).startTime();
            this.secondEndTime = dto.lessonDates().get(1).endTime();
        }

        if (dto.lessonDates().size() > 2) {
            this.thirdDate = dto.lessonDates().get(2).date();
            this.thirdStartTime = dto.lessonDates().get(2).startTime();
            this.thirdEndTime = dto.lessonDates().get(2).endTime();
        }

        if (dto.lessonDates().size() > 3) {
            this.fourthDate = dto.lessonDates().get(3).date();
            this.fourthStartTime = dto.lessonDates().get(3).startTime();
            this.fourthEndTime = dto.lessonDates().get(3).endTime();
        }

        this.cost = dto.cost();
        this.classSize = dto.studentsPerCoach();
        this.totalClassSize = dto.coaches().size() * dto.studentsPerCoach();
    }
}
