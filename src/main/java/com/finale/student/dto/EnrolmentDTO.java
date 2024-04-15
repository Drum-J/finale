package com.finale.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EnrolmentDTO {

    @Schema(description = "수강생 ID", example = "1")
    private Long studentId;

    @Schema(description = "레슨 ID", example = "1")
    private Long lessonId;
}
