package com.finale.student.dto;

import com.finale.entity.Student;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StudentCreateDTO {

    @Schema(description = "수강생 이름", example = "승호")
    private String name;

    @Schema(description = "수강생 E-mail", example = "test123@naver.com")
    private String email;

    @Schema(description = "수강생 전화번호", example = "010-1234-5678")
    private String phoneNumber;

    public Student toEntity() {
        return Student.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }
}
