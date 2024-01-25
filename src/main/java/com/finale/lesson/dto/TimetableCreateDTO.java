package com.finale.lesson.dto;

import com.finale.entity.Timetable;
import lombok.Data;

import java.util.List;

@Data
public class TimetableCreateDTO {
    private Long locationId; //장소 id
    private List<Long> coachId; //코치 id

    private String days; //레슨 요일

    private String date; //2024-01-08
    private String startTime; //07:10
    private String endTime; //08:30

    /*
    private String secondDate; //2024-01-15
    private String secondStartTime; //07:10
    private String secondEndTime; //08:30

    private String thirdDate; //2024-01-22
    private String thirdStartTime; //07:10
    private String thirdEndTime; //08:30

    private String fourthDate; //2024-01-29
    private String fourthStartTime; //07:40
    private String fourthEndTime; //09:00
    */

    private String cost; // 4회 18만원
    private int classSize; // 1:8 수업

    public Timetable convertToEntity() {
        return Timetable.builder()
                .days(this.days)
                .date(this.date)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .cost(this.cost)
                .classSize(this.classSize)
                .totalClassSize(this.classSize * this.coachId.size())
                .build();
    }
}
