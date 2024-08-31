package com.finale.scheduler;

import com.finale.common.ApiResponse;
import com.finale.entity.EnrollmentStatus;
import com.finale.lesson.repository.LessonStudentRepository;
import com.finale.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final LessonStudentRepository lessonStudentRepository;
    private final StudentRepository studentRepository;

    @Scheduled(cron = "0 0 0 25 * ?")
    public void enrollmentOpen() {
        log.info("매월 25일에 실행! 수강신청 OPEN");
        EnrollmentStatus status = scheduleRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("해당 ID의 스케줄러를 찾을 수 없습니다."));

        status.enrollmentOpen();
    }

    @Scheduled(cron = "0 0 0 15 * ?")
    public void enrollmentClose() {
        log.info("매월 15일에 실행! 수강신청 CLOSE");
        EnrollmentStatus status = scheduleRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("해당 ID의 스케줄러를 찾을 수 없습니다."));

        status.enrollmentClose();
    }

    @Scheduled(cron = "0 0 0 15 * ?")
    public void updateNewbie() {
        log.info("매월 15일 실행! 신규 -> 기존 회원으로 변경");
        List<Long> studentId = lessonStudentRepository.existsByStudentId();
        studentRepository.updateNewbie(studentId);
    }

    /**
     * 수동 수강신청 OPEN
     * @return
     */
    public ApiResponse manualEnrollmentOpen() {
        EnrollmentStatus status = scheduleRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("해당 ID의 스케줄러를 찾을 수 없습니다."));

        status.enrollmentOpen();

        return ApiResponse.successResponse("수강신청을 OPEN 하였습니다.");
    }

    /**
     * 수동 수강신청 CLOSE
     * @return
     */
    public ApiResponse manualEnrollmentClose() {
        EnrollmentStatus status = scheduleRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("해당 ID의 스케줄러를 찾을 수 없습니다."));

        status.enrollmentClose();

        return ApiResponse.successResponse("수강신청을 CLOSE 하였습니다.");
    }

    /**
     * 수동 휴식신청 OPEN
     * @return
     */
    public ApiResponse manualRestLessonOpen() {
        EnrollmentStatus status = scheduleRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("해당 ID의 스케줄러를 찾을 수 없습니다."));

        status.restLessonOpen();

        return ApiResponse.successResponse("휴식신청을 OPEN 하였습니다.");
    }

    /**
     * 수동 휴식신청 CLOSE
     * @return
     */
    public ApiResponse manualRestLessonClose() {
        EnrollmentStatus status = scheduleRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("해당 ID의 스케줄러를 찾을 수 없습니다."));

        status.restLessonClose();

        return ApiResponse.successResponse("휴식신청을 CLOSE 하였습니다.");
    }

    /**
     * 현재 수강신청 Status 확인하기
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResponse getEnrollmentStatus() {
        EnrollmentStatus status = scheduleRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("해당 ID의 스케줄러를 찾을 수 없습니다."));

        return ApiResponse.successResponse(new StatusDTO(status));
    }
}
