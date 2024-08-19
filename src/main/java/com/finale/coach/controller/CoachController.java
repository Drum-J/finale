package com.finale.coach.controller;

import com.finale.coach.dto.CopyRequestDTO;
import com.finale.coach.dto.CreateNoticeDTO;
import com.finale.coach.dto.EnrollmentSearchDTO;
import com.finale.coach.dto.LessonChangeDTO;
import com.finale.coach.dto.LessonUpdateDTO;
import com.finale.coach.dto.S3UploadDTO;
import com.finale.coach.service.CoachService;
import com.finale.coach.service.CopyService;
import com.finale.coach.service.UploadService;
import com.finale.common.ApiResponse;
import com.finale.common.FileValidator;
import com.finale.entity.CoachRole;
import com.finale.lesson.dto.TimetableCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coach")
public class CoachController {

    private final CoachService coachService;
    private final CopyService copyService;
    private final UploadService uploadService;

    @GetMapping("/list")
    @Operation(summary = "코치 리스트 API", description = "getCoachList()")
    public ApiResponse getCoachList() {
        return coachService.getCoachList();
    }

    @Operation(summary = "[MASTER] 코치 권한 변경 API", description = "코치 권한이 [MASTER]인 경우만 가능합니다.")
    @PostMapping("/updateRole/{id}")
    public ApiResponse updateRole(@PathVariable("id") Long id) {
        return coachService.updateRole(id);
    }

    @Operation(summary = "레슨 생성 API", description = "createTimetable()")
    @PostMapping("/createLesson")
    public ApiResponse createTimetable(@RequestBody TimetableCreateDTO dto) {
        return coachService.createTimetable(dto);
    }

    @Operation(summary = "[MASTER] 수강생 입금 확인 API", description = "코치 권한이 [MASTER]인 경우만 가능합니다.")
    @PostMapping("/depositConfirm/{id}")
    public ApiResponse depositConfirm(@PathVariable("id") Long id) {
        return coachService.updateDeposit(id);
    }

    @Operation(summary = "[코치용] 모든 레슨 조회 API", description = "코치용 모든 레슨 조회 API입니다.")
    @GetMapping("/lesson/list")
    public ApiResponse getAllLesson() {
        return coachService.getAllLessonForCoach();
    }

    @Operation(summary = "[코치용] 장소 별 레슨 조회 API")
    @GetMapping("/lesson/withLocation/{locationName}")
    public ApiResponse lessonsByLocation(@PathVariable("locationName") String name) {
        return coachService.getLessonsByLocationForCoach(name);
    }

    @Operation(summary = "[코치용] 레슨 디테일 조회 API", description = "코치용 레슨 디테일 조회 입니다. 레슨 ID 값이 필요합니다.")
    @GetMapping("/lesson/{id}")
    public ApiResponse getLessonDetails(@PathVariable(name = "id") Long id) {
        return coachService.getLessonDetailsForCoach(id);
    }

    @Operation(summary = "[코치용] 레슨 복사 API")
    @PostMapping("/lesson/copy")
    public ApiResponse lessonCopy(CopyRequestDTO dto) {
        return copyService.lessonCopy(dto);
    }

    @Operation(summary = "[코치용] 레슨 공지사항 생성 API")
    @PostMapping("/createNotice")
    public ApiResponse createNotice(@RequestBody CreateNoticeDTO dto) {
        return coachService.createNotice(dto);
    }

    @Operation(summary = "[코치용] 전체 수강생 리스트")
    @GetMapping("/studentList")
    public ApiResponse studentList() {
        return coachService.getStudentList();
    }

    @Operation(summary = "[코치용] 수강신청 현황 리스트")
    @GetMapping("/enrollmentList")
    public ApiResponse enrollmentList(EnrollmentSearchDTO dto) {
        return coachService.getEnrollmentList(dto);
    }

    @Operation(summary = "[코치용] 코치 데이터 수정 API")
    @PostMapping("/update")
    public ApiResponse updateCoach(@ModelAttribute S3UploadDTO dto) {
        log.info("===== 코치 Update Controller 진입 =====");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id = Long.parseLong(authentication.getPrincipal().toString());
        boolean contains = authentication.getAuthorities().contains(new SimpleGrantedAuthority(CoachRole.MASTER.toString()));

        if (!(id == dto.id() || contains)) {
            throw new IllegalStateException("본인 또는 운영자만 변경할 수 있습니다.");
        }

        if (!dto.file().isEmpty() && !FileValidator.isImage(dto.file().getOriginalFilename())) {
            throw new IllegalStateException("이미지 확장자를 확인해 주세요. [jpg,jpeg,png,gif] 파일만 가능합니다.");
        }

        if (dto.file().isEmpty()) {
            return coachService.updateCoach(dto);
        }

        return uploadService.coachProfileUpload(dto);
    }

    @Operation(summary = "[코치용] 수강신청 취소 API")
    @PostMapping("/lessonCancel/{id}")
    public ApiResponse lessonCancel(@PathVariable("id") Long id) {
        return coachService.lessonCancel(id);
    }

    @Operation(summary = "[코치용] 수강변경 API")
    @PostMapping("/lessonChange")
    public ApiResponse lessonChange(LessonChangeDTO dto) {
        return coachService.lessonChange(dto);
    }

    @Operation(summary = "[코치용] 레슨 업데이트 API")
    @PostMapping("/updateLesson")
    public ApiResponse updateLesson(@RequestBody LessonUpdateDTO dto) {
        return coachService.updateLesson(dto);
    }
}
