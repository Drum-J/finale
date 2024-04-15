package com.finale.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class StudentDTO {

    @Schema(description = "수강생 ID", example = "1")
    private Long id;

    @Schema(description = "수강생 이름", example = "학생1")
    private String name;

    public StudentDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
