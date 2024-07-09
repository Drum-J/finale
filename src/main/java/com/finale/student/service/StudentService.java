package com.finale.student.service;

import com.finale.common.ApiResponse;
import com.finale.entity.Lesson;
import com.finale.entity.LessonStudent;
import com.finale.exception.ResourceNotFoundException;
import com.finale.student.dto.EnrolmentDTO;
import com.finale.entity.Student;
import com.finale.student.repository.StudentRepository;
import com.finale.lesson.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    public ApiResponse enrolment(EnrolmentDTO dto) {
        log.info("=== 수강 신청 Service 진입 ===");
        log.info("수강 신청 데이터 = {}",dto);
        Lesson lesson = lessonRepository.findById(dto.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 레슨을 찾을 수 없습니다."));

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 학생을 찾을 수 없습니다."));

        LessonStudent lessonStudent = new LessonStudent(lesson, student);

        lesson.addStudent(lessonStudent);

        return ApiResponse.successResponse("수강신청이 정상적으로 완료 되었습니다.");
    }
}
