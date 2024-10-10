package com.finale.student.service;

import com.finale.common.ApiResponse;
import com.finale.entity.EnrollmentStatus;
import com.finale.entity.Lesson;
import com.finale.entity.LessonStudent;
import com.finale.entity.Timetable;
import com.finale.exception.ResourceNotFoundException;
import com.finale.lesson.repository.LessonStudentRepository;
import com.finale.scheduler.ScheduleRepository;
import com.finale.sms.SmsService;
import com.finale.student.dto.EnrolmentDTO;
import com.finale.entity.Student;
import com.finale.student.dto.MyPageDTO;
import com.finale.student.repository.StudentRepository;
import com.finale.lesson.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;
    private final LessonStudentRepository lessonStudentRepository;
    private final ScheduleRepository scheduleRepository;
    private final SmsService smsService;

    @Transactional
    public ApiResponse enrolment(EnrolmentDTO dto) throws CoolsmsException {
        log.info("=== 수강 신청 Service 진입 ===");
        log.info("수강 신청 데이터 = {}",dto);

        EnrollmentStatus status = scheduleRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("해당 ID의 스케줄러를 찾을 수 없습니다."));

        log.info("수강신청 가능 여부 = {}",status.isEnrollmentStatus());

        if (status.isEnrollmentStatus()) {
            Lesson lesson = lessonRepository.findByIdAndDelYnIsFalse(dto.getLessonId())
                    .orElseThrow(() -> new ResourceNotFoundException("해당 레슨을 찾을 수 없습니다."));

            Student student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("해당 학생을 찾을 수 없습니다."));

            LessonStudent lessonStudent = new LessonStudent(lesson, student);

            lesson.addStudent(lessonStudent);

            smsService.applySend(lesson.getTimetable(),student);

            return ApiResponse.successResponse("수강신청이 정상적으로 완료 되었습니다.");
        } else {
            throw new IllegalStateException("수강신청 기간이 아닙니다.");
        }


    }

    @Transactional
    public ApiResponse restLesson(Long id, Long userId) {
        log.info("=== 휴식 신청 Service 진입 ===");
        log.info("휴식 신청 LessonStudent ID = {}",id);

        EnrollmentStatus status = scheduleRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("해당 ID의 스케줄러를 찾을 수 없습니다."));

        log.info("휴식신청 가능 여부 = {}",status.isRestLessonStatus());

        if (status.isRestLessonStatus()) {
            LessonStudent lessonStudent = lessonStudentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("해당 레슨을 찾을 수 없습니다."));

            if (!userId.equals(lessonStudent.getStudent().getId())) {
                throw new IllegalStateException("본인의 레슨만 휴식 신청 할 수 있습니다.");
            }

            lessonStudent.restLesson();
            return ApiResponse.successResponse("휴식신청이 정상적으로 완료 되었습니다.");
        } else {
            throw new IllegalStateException("휴식신청 기간이 아닙니다.");
        }
    }

    public ApiResponse getMyPage(Long id) {
        List<LessonStudent> byStudentId = lessonStudentRepository.findByStudentId(id);

        List<MyPageDTO> list = byStudentId.stream()
                .filter(lessonStudent -> !lessonStudent.isRestLesson()).map(MyPageDTO::new).toList();

        return ApiResponse.successResponse(list);
    }

    @Transactional
    public ApiResponse lessonCancel(Long id, Long userId) throws CoolsmsException {
        LessonStudent lessonStudent = lessonStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 레슨을 찾을 수 없습니다."));

        if (!userId.equals(lessonStudent.getStudent().getId())) {
            throw new IllegalStateException("본인의 레슨만 취소 할 수 있습니다.");
        }

        lessonStudent.getLesson().enrolmentMinus();

        Timetable timetable = lessonStudent.getLesson().getTimetable();
        Student student = lessonStudent.getStudent();

        smsService.cancelSend(timetable, student);

        lessonStudentRepository.delete(lessonStudent);

        return ApiResponse.successResponse("수강취소를 완료 했습니다.");
    }
}
