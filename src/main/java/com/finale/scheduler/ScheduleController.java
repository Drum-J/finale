package com.finale.scheduler;

import com.finale.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private static final String OPEN = "open";
    private static final String CLOSE = "close";

    @PostMapping("/enrollment/{type}")
    @Operation(summary = "수강신청 가능 여부 변경 API" , description = "type은 open / close 만 가능합니다.")
    public ApiResponse enrollment(@PathVariable("type") String type) {
        if (OPEN.equals(type)) {
            return scheduleService.manualEnrollmentOpen();
        } else if (CLOSE.equals(type)) {
            return scheduleService.manualEnrollmentClose();
        } else {
            return ApiResponse.badRequestResponse("TYPE 값이 올바르지 않습니다.");
        }
    }

    @PostMapping("/restLesson/{type}")
    @Operation(summary = "휴식신청 가능 여부 변경 API" , description = "type은 open / close 만 가능합니다.")
    public ApiResponse restLesson(@PathVariable("type") String type) {
        if (OPEN.equals(type)) {
            return scheduleService.manualRestLessonOpen();
        } else if (CLOSE.equals(type)) {
            return scheduleService.manualRestLessonClose();
        } else {
            return ApiResponse.badRequestResponse("TYPE 값이 올바르지 않습니다.");
        }
    }

    @GetMapping("/status")
    @Operation(summary = "수강신청 및 휴식신청 가능 여부 확인 API" , description = "data의 값이 true 일 경우 가능, false 일 경우 불가능")
    public ApiResponse enrollmentStatus() {
        return scheduleService.getEnrollmentStatus();
    }
}
