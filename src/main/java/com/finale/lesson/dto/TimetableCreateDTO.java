package com.finale.lesson.dto;

import com.finale.entity.Timetable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class TimetableCreateDTO {
    @Schema(description = "수업 제목", example = "2024년 1월 월요일 계명대학교")
    private String title;

    @Schema(description = "장소", example = "계명대학교")
    private String location;

    @Schema(description = "코치 ID List", example = "[1,2]")
    private List<Long> coachId;

    @Schema(description = "레슨 요일", example = "월요일")
    private String days;

    @Schema(description = "첫 번째 수업 날짜", example = "2024-01-08")
    private String date;
    @Schema(description = "첫 번째 수업 시작 시간", example = "07:10")
    private String startTime;
    @Schema(description = "첫 번째 수업 종료 시간", example = "08:30")
    private String endTime;

    @Schema(description = "두 번째 수업 날짜", example = "2024-01-15")
    private String secondDate;
    @Schema(description = "두 번째 수업 시작 시간", example = "07:10")
    private String secondStartTime;
    @Schema(description = "두 번째 수업 종료 시간", example = "08:30")
    private String secondEndTime;

    @Schema(description = "세 번째 수업 날짜", example = "2024-01-22")
    private String thirdDate;
    @Schema(description = "세 번째 수업 시작 시간", example = "07:10")
    private String thirdStartTime;
    @Schema(description = "세 번째 수업 종료 시간", example = "08:30")
    private String thirdEndTime;

    @Schema(description = "네 번째 수업 날짜", example = "2024-01-29")
    private String fourthDate;
    @Schema(description = "네 번째 수업 시작 시간", example = "07:40")
    private String fourthStartTime;
    @Schema(description = "네 번째 수업 종료 시간", example = "09:00")
    private String fourthEndTime;

    @Schema(description = "수업 횟수 및 비용", example = "4회 18만원")
    private String cost;
    @Schema(description = "(코치 1명 당) 수업 정원", example = "8")
    private int classSize;

    public Timetable convertToEntity() {
        return Timetable.builder()
                .title(this.title)
                .location(this.location)
                .days(this.days)
                .date(this.date)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .secondDate(this.secondDate)
                .secondStartTime(this.secondStartTime)
                .secondEndTime(this.secondEndTime)
                .thirdDate(this.thirdDate)
                .thirdStartTime(this.thirdStartTime)
                .thirdEndTime(this.thirdEndTime)
                .fourthDate(this.fourthDate)
                .fourthStartTime(this.fourthStartTime)
                .fourthEndTime(this.fourthEndTime)
                .cost(this.cost)
                .classSize(this.classSize)
                .totalClassSize(this.classSize * this.coachId.size())
                .build();
    }
}
