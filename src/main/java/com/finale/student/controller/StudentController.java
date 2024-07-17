package com.finale.student.controller;

import com.finale.common.ApiResponse;
import com.finale.student.dto.EnrolmentDTO;
import com.finale.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 마이페이지에서 휴식 신청 가능하게 만들까 생각 중
     * 마이페이지에 내가 신청한 레슨은 LessonStudent 의 값을 가져와서 보여주기
     *
     * @param id
     * @return
     */
    @Operation(summary = "[수강생용] 레슨 휴식 신청 API")
    @PostMapping("/restLesson/{id}")
    public ApiResponse restLesson(@PathVariable("id") Long id) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return studentService.restLesson(id,Long.parseLong(userId));
    }

    @Operation(summary = "[수강생용] 마이 페이지 API")
    @GetMapping("/myPage")
    public ApiResponse getMyPage() {
        log.info("=== 마이 페이지 Controller 진입 ===");
        String id = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        log.info("로그인한 수강생 ID = {}", id);
        return studentService.getMyPage(Long.parseLong(id));
    }
}
