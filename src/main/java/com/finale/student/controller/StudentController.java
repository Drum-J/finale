package com.finale.student.controller;

import com.finale.common.ApiResponse;
import com.finale.student.dto.EnrolmentDTO;
import com.finale.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ApiResponse enrolment(@RequestBody EnrolmentDTO dto) {
        log.info("=== 수강 신청 Controller 진입 ===");
        Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("로그인한 수강생 ID = {}", id);
        dto.setStudentId(Long.parseLong(id.toString()));
        return studentService.enrolment(dto);
    }
}
