package com.finale.student.controller;

import com.finale.student.dto.EnrolmentDTO;
import com.finale.student.dto.StudentCreateDTO;
import com.finale.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/enrolment")
    @Operation(summary = "수강생 레슨 신청 API", description = "enrolment()")
    public String enrolment(@RequestBody EnrolmentDTO dto) {
        return studentService.enrolment(dto);
    }

    @PostMapping("/create")
    @Operation(summary = "수강생 생성 API", description = "createStudent()")
    public String createStudent(@RequestBody StudentCreateDTO dto) {
        return studentService.create(dto);
    }
}
