package com.finale.student.dto;

import com.finale.entity.Student;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class StudentDTO {

    @Schema(description = "수강생 ID", example = "1")
    private final Long id;

    @Schema(description = "수강생 이름", example = "학생1")
    private final String name;

    @Schema(description = "수강생 전화 번호", example = "010-1234-5678")
    private final String phoneNumber;

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.phoneNumber = student.getPhoneNumber();
    }
}
