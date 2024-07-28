package com.finale.scheduler;

import com.finale.common.ApiResponse;
import com.finale.entity.EnrollmentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Scheduled(cron = "0 0 0 14 * ?")
    public void enrollmentOpen() {
        EnrollmentStatus status = scheduleRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("해당 ID의 스케줄러를 찾을 수 없습니다."));

        status.enrollmentOpen();
    }

    @Scheduled(cron = "0 0 0 20 * ?")
    public void enrollmentClose() {
        EnrollmentStatus status = scheduleRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("해당 ID의 스케줄러를 찾을 수 없습니다."));

        status.enrollmentClose();
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
    public ApiResponse getEnrollmentStatus() {
        EnrollmentStatus status = scheduleRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("해당 ID의 스케줄러를 찾을 수 없습니다."));

        return ApiResponse.successResponse(new StatusDTO(status));
    }
}
